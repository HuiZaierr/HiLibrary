package com.ych.hilibrary.util


import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.Process
import android.os.StatFs
import android.text.format.Formatter
import com.ych.hilibrary.BuildConfig
import com.ych.hilibrary.log.HiLog
import com.ych.hilibrary.manager.ActivityManager
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*


object CrashHandler {
    const val CRASH_DIR = "crash_dir"
    const val TAG = "CrashHandler"
    init {
        //设置我们自己的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(CaughtExceptionHandler())
    }


    class CaughtExceptionHandler:Thread.UncaughtExceptionHandler{
        private val context = AppGlobals.get()!!
        private val defaultUncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = Thread.getDefaultUncaughtExceptionHandler()
        private val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA)
        private val LAUNCH_TIME = formatter.format(Date())
        override fun uncaughtException(t: Thread, e: Throwable) {
            //是否可以处理异常，可以就自己处理，否则就交由系统默认的DefaultUncaughtExceptionHandler来处理
            if (!handlerException(e) && defaultUncaughtExceptionHandler == null){
                //交由系统默认异常处理器处理
                defaultUncaughtExceptionHandler!!.uncaughtException(t,e)
            }else{
                //自己处理
                //1.重启应用
                restartApp()
            }
        }

        private fun restartApp() {
            val intent :Intent? = context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            //启动新的Activity
            context.startActivity(intent)
            //并将当前的进程杀死
            Process.killProcess(Process.myPid())
            System.exit(10)
        }


        private fun handlerException(e: Throwable): Boolean {
            if (e == null)  return false
            val log = collectDeviceInfo(e)
            //如果是Debug时就打印控制台
//            if (BuildConfig.DEBUG){
//                HiLog.et(TAG, "错误信息：$log")
//            }else{
                HiLog.e("TAG","写入文件")
                //写入到文件，并在某时刻上传到服务器
                saveCrashInfo2File(log)
//            }
            return true
        }


        private fun saveCrashInfo2File(log: String) {
            /**
             * 创建文件夹：指定路径，和文件夹名称
             * 参数1：父路径
             * 参数2：子文件名
             */
            var crashDir = File(context.cacheDir,CRASH_DIR)
            //判断文件是否存在
            if (!crashDir.exists()){
                crashDir.mkdirs()   //创建文件
            }

            /**
             * 创建文件，指定文件路径，和文件名称
             * 参数1：文件存储路径
             * 参数2：文件名，以当前时间为文件名
             */
            val crashFile = File(crashDir,formatter.format(Date())+"-crash.txt")
            crashFile.createNewFile()
            //通过输出流写入文件
            val fos = FileOutputStream(crashFile)
            try {
                fos.write(log.toByteArray())
                fos.flush()
            }catch (ex:Exception){
                ex.printStackTrace()
            }finally {
                fos.close()
            }
        }

        /**
         * 收集设备信息，OS版本,线程名，前后台，使用时长，App版本，升级渠道
         * CPU架构，内存信息，存储信息，permission权限
         */
        private fun collectDeviceInfo(e: Throwable): String {
            val sb = StringBuilder()
            //1.手机设备信息
            sb.append("brand = ${Build.BOARD}\n")                           //品牌 xiaomi huawei
            sb.append("rom = ${Build.MODEL}\n")                             //sm-G9550
            sb.append("os = ${Build.VERSION.RELEASE}\n")                    //OS版本 Android9.0
            sb.append("sdk = ${Build.VERSION.SDK_INT}\n")                   //sdk版本 28 29 30
            sb.append("launch_time = ${LAUNCH_TIME}\n")                     //App启动时间
            sb.append("crash_time = ${formatter.format(Date())}\n")         //crash发生的时间
            sb.append("forground = ${ActivityManager.instance.front}\n")    //crash发生时是在前台还是后台
            sb.append("threadName = ${Thread.currentThread().name}\n")      //crash发生时线程名
            sb.append("CPU架构 = ${Build.CPU_ABI}\n")                       //CPU架构 arm-v7 arm-v8
            sb.append("CPU架构 = ${Build.CPU_ABI}\n")                       //CPU架构

            //2.App信息
            val packageInfo = context.packageManager.getPackageInfo(context.packageName,0)
            sb.append("version_code=${packageInfo.versionCode}\n")            //版本号v1.0
            sb.append("version_name=${packageInfo.versionName}\n")            //版本名称
            sb.append("package_name=${packageInfo.packageName}\n")            //包名 app.xxx.xx
            sb.append("permission_name=${Arrays.toString(packageInfo.requestedPermissions)}\n")  //已申请的权限名称

            //3.统计磁盘存储信息
            val menInfo = android.app.ActivityManager.MemoryInfo()
            val ams = context.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            ams.getMemoryInfo(menInfo)
            sb.append("alailMen=${Formatter.formatFileSize(context,menInfo.availMem)}\n")  //可用内存大小转换为GB
            sb.append("totalMem=${Formatter.formatFileSize(context,menInfo.totalMem)}\n")  //总内存大小

            val file = Environment.getExternalStorageDirectory()
            val statFs = StatFs(file.path)
            val avaiableSize = statFs.availableBlocks * statFs.blockSize
            sb.append("availStorage=${Formatter.formatFileSize(context,avaiableSize.toLong())}\n")  //磁盘存储空间

            //4.异常信息
            val write = StringWriter()
            val printWriter = PrintWriter(write)
            e.printStackTrace(printWriter)
            var cause = e.cause
            //打印出所有异常信息，从最顶层开始
            while (cause!=null){
                cause.printStackTrace(printWriter)
                cause = cause.cause
            }
            printWriter.close()
            sb.append(write.toString())

            return sb.toString()
        }
    }
}