package com.ych.hilibrary.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ych.hilibrary.util.AppGlobals

/**
 * TODO：数据库对象
 *    注意：1.它必须是一个abstract class。
 *         2.它还需要为RoomDatabase的子类。
 *         3.需要标记@Database()注解.
 *              3.1.必须添加entities属性，表示数据库有哪些表。
 *              3.2.必须指定数据的版本version
 *              3.3.可以指定exportSchema为true，表示编译时会为数据库表生成json文件，但是要在对应的gradle中添加
 *                  //指定room.schemaLocation生成的文件路径
                    javaCompileOptions {
                        annotationProcessorOptions {
                            arguments = ["room.schemaLocation": "$projectDir/schemas".toString()]
                        }
                    }
 *
 */
//@Database(entities = [Cache::class],version = 1,exportSchema = true)
@Database(entities = [Cache::class],version = 1)
abstract class CacheDatabase :RoomDatabase(){

    //获取cacheDao的，也可以定义为抽象方法
    abstract val cacheDao:CacheDao

    //在任意地方都可以使用CacheDatabase，我们定义为单例
    companion object{
        private var database:CacheDatabase

        @Synchronized
        fun get():CacheDatabase{
            return database
        }

        init {
               val context = AppGlobals.get()!!.applicationContext
                //创建数据库,下面两种方法都可以创建
                database = Room.databaseBuilder(context,CacheDatabase::class.java,"howow_cache")
//                  .allowMainThreadQueries()               //允许在主线程中操作数据类，默认是不允许的
//                  .addCallback(callback)                  //数据库创建和打开后的回掉。接口方法，onCreate，onOpen，创建和打开。
//                  .setQueryExecutor()                     //设置查询线程池的，默认是 ArchTaskExecutor.getIOThreadExecutor()
//                  .openHelperFactory()                    //对SQliteOpenHelper提供的一个工厂类
//                  .setJournalMode()                       //room数据库的日志模式
//                  .fallbackToDestructiveMigration()       //数据库升级异常之后的回滚，会重新创建数据库，删除Room管理的数据库表中的所有数据
//                  .fallbackToDestructiveMigrationFrom()   //数据库升级异常之后指定版本进行回滚
//                  .addMigrations(migration)               //数据库升级需要添加数据库迁移，1-2 2-3  1-3只要结束版本大于开始版本
                    .build()

                //他表示创建内存数据库，这种数据库当中存储的数据，只会存留在内存当中，进程被杀死后，数据随之丢失。一般不用
                //cacheDatabase = Room.inMemoryDatabaseBuilder(context,CacheDatabase::class.java).build()
            }
        }

//        val callback = object : RoomDatabase.Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//                super.onCreate(db)
//            }
//
//            override fun onOpen(db: SupportSQLiteDatabase) {
//                super.onOpen(db)
//            }
//        }
//
//        //数据库迁移，每当数据库发生变化，数据库版本都需要该变，也需要设置migration
//        val migration = object:Migration(1,2){
//            override fun migrate(database: SupportSQLiteDatabase) {
////                database.execSQL()
//            }
//        }
//    }
}