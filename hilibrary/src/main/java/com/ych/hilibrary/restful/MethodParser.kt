package com.ych.hilibrary.restful

import com.ych.hilibrary.restful.annotation.*
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.IllegalStateException

/**
 * TODO：解析接口函数的注解，参数，返回值等信息
 */
class MethodParser(val baseUrl:String,method:Method,arg:Array<Any>) {

    private lateinit var domainUrl: String
    //POST请求是否使用表单提交
    private var formPost: Boolean? = false
    //方法的请求方式：GET == 0  POST == 1
    private var httpMethod: Int = 0
    //方法的相对路径
    private lateinit var relativeUrl: String
    //方法的返回值类型
    private var returnType: Type? = null
    //通过可变集合Map存储header的信息
    private var headers:MutableMap<String,String?> = mutableMapOf()
    //方法参数集合
    private var parameters:MutableMap<String,Any> = mutableMapOf()

    init {
        //解析方法的注解信息
        parseMethodAnnotations(method)

        //解析方法的参数
        parseMethodParameters(method,arg)

        //解析方法的返回值
        parseMethodReturnType(method)
    }

    /**
     * TODO：解析方法的返回类型，以及返回泛型参数
     *    返回类型为HiCall，泛型参数只能一个。
     */
    private fun parseMethodReturnType(method: Method) {
        //如果接口方法的返回类型不是HiCall，就抛出一个异常
        if (method.returnType!=HiCall::class.java){
            throw IllegalStateException(String.format("method %s must be type of HiCall.class",method.name))
        }
        //获取方法返回的泛型类型
        val genericReturnType = method.genericReturnType
        //如果有泛型参数，只能有一个泛型参数
        if (genericReturnType is ParameterizedType){
            val actualTypeArguments = genericReturnType.actualTypeArguments
            require(actualTypeArguments.size == 1){String.format("methiod %s can only has one generic return type",method.name)}
            returnType =  actualTypeArguments[0]
        }else{
            //没有泛型参数，也需要抛出异常
            throw IllegalStateException(String.format("methiod %s must has one gerneric return type",method.name))
        }
    }

    /**
     * TODO：解析方法的参数，@Path @Filed
     */
    private fun parseMethodParameters(method: Method, arg: Array<Any>) {
        val parameterAnnotations = method.parameterAnnotations
        //获取方法参数注解,是否等于参数注解个数
        val equals = method.parameterAnnotations.size == arg.size
        require(equals){ String.format("arguments annotations count %s dont match expect count %s",parameterAnnotations.size,arg.size)}
        for (index in arg.indices){
            val annotations = parameterAnnotations[index]
            require(annotations.size <= 1){ String.format("filed can only has one annotation :index = $index")}
            val value = arg[0]
            require(isPrimitive(value)){"8 nasic types are supporyed for now,index=$index"}

            val annotation = annotations[0]
            if (annotation is Filed){
                val key = annotation.value
                var value = arg[index]
                parameters[key] = value
            }else if (annotation is Path){
                val replaceName = annotation.value
                var replacement = value.toString()
                if (replaceName!=null && replacement!=null){
                    val newRelativeUtl = relativeUrl.replace("${replaceName}",replacement)
                    relativeUrl = newRelativeUtl
                }
            }else{
                throw IllegalStateException("cannot handle parameter annotation :${annotation.javaClass.toString()}")
            }
        }
    }

    /**
     * 判断方法的参数类型是否是基本数据类型，
     */
    private fun isPrimitive(value: Any) :Boolean{
        //单独处理String类型
        if (value.javaClass == String::class.java){
            return true
        }
        try {
            //int byte short long char boolean double float
            val field = value.javaClass.getField("TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive){
                return true
            }
        }catch (e:IllegalAccessError){
            e.printStackTrace()
        }catch (e:NoSuchFieldException){
            e.printStackTrace()
        }
        return false
    }


    /**
     * TODO：解析方法的注解信息，比如GET,POST,Headers等等
     */
    private fun parseMethodAnnotations(method: Method) {
        //获取方法的所有注解
        val annotations = method.annotations
        //遍历每一个注解，进行处理
        for (annotation in annotations){
//            when(annotation){
//                is GET -> {
//                    //就是方法声明的相对路径
//                    relativeUrl = annotation.value
//                    httpMethod = HiRequest.METHOD.GET
//                }
//                is POST -> {
//
//                }
//            }

            //如果为GET，获取它的值
            if (annotation is GET){
                //就是方法声明的相对路径
                relativeUrl = annotation.value
                httpMethod = HiRequest.METHOD.GET
            }else if (annotation is POST){
                relativeUrl = annotation.value
                httpMethod = HiRequest.METHOD.POST
                //post请求是否使用表单提交FormUrlEncoded
                formPost = annotation.formPost
            }else if (annotation is Headers){
                //获取headers的信息
                val headersArray = annotation.value
                //遍历headers中每一个信息
                //例如：@Headers("auth-token:token","accountId:123456")
                for (header in headersArray){
                    //获取冒号位置
                    val colon = header.indexOf(":")
                    check(!(colon==0||colon==-1)){
                        String.format("@headers value must be in the form [name:value] ,but found [%s]",header)
                    }
                    val name = header.substring(0,colon)
                    val value = header.substring(colon+1).trim()
                    headers[name] = value
                }
            }else if (annotation is BaseUrl){
                domainUrl = annotation.value
            }else{
                //抛出异常
                throw IllegalStateException("cannot handle method annotation:${annotation.javaClass.toString()}")
            }
            require(!(httpMethod!=HiRequest.METHOD.GET) && (httpMethod!=HiRequest.METHOD.POST)){
                String.format("method %s must has one of GET,POST",method.name)
            }

            if (domainUrl == null){
                domainUrl = baseUrl
            }
        }
    }

    fun newRequest(): HiRequest {
        var request = HiRequest()
        request.domainUrl = domainUrl
        request.headers = headers
        request.returnType = returnType
        request.relativeUrl = relativeUrl
        request.parameters = parameters
        request.httpMethod = httpMethod
        return request
    }

    /**
     * 伴生对象，相当于Java的静态类
     */
    companion object{
        fun parse(baseUrl:String,method:Method,arg:Array<Any>):MethodParser{
            return MethodParser(baseUrl,method,arg)
        }
    }
}