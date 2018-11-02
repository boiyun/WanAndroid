package com.gank.chen.http;

import com.gank.chen.mvp.model.ArticleModel;
import com.gank.chen.mvp.model.BannerModel;
import com.gank.chen.mvp.model.BaseGankModel;
import com.gank.chen.mvp.model.BaseModel;
import com.gank.chen.mvp.model.CarsListModel;
import com.gank.chen.mvp.model.ChaptersListModel;
import com.gank.chen.mvp.model.CommonWebsiteModel;
import com.gank.chen.mvp.model.MeiZi;
import com.gank.chen.mvp.model.RegisterModel;
import com.gank.chen.mvp.model.VideoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author chen
 * @date 2017/12/17
 */

public interface ApiUtil {
    String GANK_HOST = "use_host:gank";
    String WAN_HOST = "use_host:wan";

    /**
     * 福利
     *
     * @param page page
     * @return List<MeiZi>
     */
    @Headers(GANK_HOST)
    @GET("api/data/福利/" + ApiConfig.GANK_SIZE + "/{page}")
    Flowable<BaseGankModel<List<MeiZi>>> getGankData(@Path("page") int page);

    /**
     * android\iOS\休息视频\前端
     *
     * @param page page
     * @return List<VideoBean>
     */
    @Headers(GANK_HOST)
    @GET("api/data/{type}/" + ApiConfig.GANK_SIZE + "/{page}")
    Flowable<BaseGankModel<List<VideoBean>>> getAndroidList(@Path("page") int page, @Path("type") String type);

    /**
     * 测试code=401
     *
     * @return CarsListModel
     */
    @Headers(WAN_HOST)
    @GET("/tools/mockapi/4633/401code")
    Observable<BaseModel<CarsListModel>> getXianDuCategoriess();

    /**
     * 注册
     *
     * @return RegisterModel
     */
    @Headers(WAN_HOST)
    @FormUrlEncoded
    @POST(UrlManager.REGISTER)
    Observable<BaseModel<RegisterModel>> Register(@FieldMap Map<String, String> map);

    /**
     * 登录
     *
     * @return RegisterModel
     */
    @Headers(WAN_HOST)
    @FormUrlEncoded
    @POST(UrlManager.LOGIN)
    Observable<BaseModel<RegisterModel>> toLogin(@FieldMap Map<String, String> map);

    /**
     * 退出登录
     *
     * @return
     */
    @Headers(WAN_HOST)
    @GET(UrlManager.LOGOUT)
    Observable<BaseModel> toLogout();

    /**
     * 获取banner
     *
     * @return ArrayList<BannerModel>
     */
    @Headers(WAN_HOST)
    @GET(UrlManager.BANNER)
    Observable<BaseModel<ArrayList<BannerModel>>> getBanner();

    /**
     * 首页文章列表
     *
     * @param page
     * @return ArrayList<BannerModel>
     */
    @Headers(WAN_HOST)
    @GET(UrlManager.ARTICLE_LIST)
    Observable<BaseModel<ArticleModel>> getArticleList(@Path("page") int page);

    /**
     * 收藏文章列表
     *
     * @param page
     * @return ArticleModel
     */
    @Headers(WAN_HOST)
    @GET(UrlManager.COLLECT_LIST)
    Observable<BaseModel<ArticleModel>> getCollectList(@Path("page") int page);

    /**
     * 收藏站内文章
     *
     * @param id
     * @return BaseModel
     */
    @Headers(WAN_HOST)
    @POST(UrlManager.COLLECT)
    Observable<BaseModel> toCollect(@Path("id") int id);

    /**
     * 取消收藏站内文章(文章列表)
     *
     * @param id
     * @return BaseModel
     */
    @Headers(WAN_HOST)
    @POST(UrlManager.UN_COLLECT)
    Observable<BaseModel> toUnCollect(@Path("id") int id);

    /**
     * 取消收藏站内文章(我的收藏)
     *
     * @param id
     * @param originId
     * @return BaseModel
     */
    @Headers(WAN_HOST)
    @POST(UrlManager.UN_COLLECT_FROM_MINE)
    Observable<BaseModel> toUnCollectFromMine(@Path("id") int id, @Query("originId") int originId);

    /**
     * 获取公众号列表
     *
     * @return List<ChaptersListModel>
     */
    @Headers(WAN_HOST)
    @POST(UrlManager.CHAPTERS)
    Observable<BaseModel<List<ChaptersListModel>>> toGetChapter();


    /**
     * 收藏站内文章
     *
     * @param id
     * @return BaseModel
     */
    @Headers(WAN_HOST)
    @GET(UrlManager.CHAPTERS_LIST)
    Observable<BaseModel<ArticleModel>> toGetChaptersList(@Path("id") int id, @Path("page") int page);

    /**
     * 获取常用网站
     *
     * @return List<CommonWebsiteModel>
     */
    @Headers(WAN_HOST)
    @POST(UrlManager.COMMON_WEBSITE)
    Observable<BaseModel<List<CommonWebsiteModel>>> toGetCommonWebsite();

    /**
     * 获取热搜词
     *
     * @return List<SearchModel>
     */
    @Headers(WAN_HOST)
    @GET(UrlManager.HOT_KEY)
    Observable<BaseModel<List<CommonWebsiteModel>>> getHotSearchData();

    /**
     * 搜索
     *
     * @return ArticleModel
     */
    @Headers(WAN_HOST)
    @POST(UrlManager.QUERY)
    Observable<BaseModel<ArticleModel>> toQuary(@Path("page") int page, @Query("k") String key);
}
