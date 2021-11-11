package com.ych.retrofit;


//import io.reactivex.Observable;
//import io.reactivex.ObservableSource;
//import io.reactivex.ObservableTransformer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;

/**
 * Created by ych on 2018/2/3.
 */
public class RxUtil {

//    /*
//     * 1.x使用
//     * 统一线程处理
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<T, T> rxSchedulerHelper() {    //compose简化线程
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> observable) {
//                return observable.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }

//    /**
//     * 2.x使用
//     * 统一线程处理
//     * @param <T>
//     * @return
//     */
//    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<T> observable) {
//                return observable.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }

}
