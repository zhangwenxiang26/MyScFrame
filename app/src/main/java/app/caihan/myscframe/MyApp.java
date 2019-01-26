package app.caihan.myscframe;import android.content.Context;import com.caihan.scframe.framework.ScApplication;import com.caihan.scframe.utils.ScOutdatedUtils;import com.caihan.scframe.utils.json.JsonAnalysis;import com.scwang.smartrefresh.header.MaterialHeader;import com.scwang.smartrefresh.layout.SmartRefreshLayout;import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;import com.scwang.smartrefresh.layout.api.RefreshHeader;import com.scwang.smartrefresh.layout.api.RefreshLayout;import com.squareup.leakcanary.LeakCanary;import com.squareup.leakcanary.RefWatcher;/** *  * * @author caihan * @date 2018/4/22 * @e-mail 93234929@qq.com * 维护者  */public class MyApp extends ScApplication {    public static final String TAG = "MyScFrame";    private RefWatcher refWatcher;    private static Context sContext;    static {        //设置全局的Header构建器        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {            @Override            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {                //下拉刷新                MaterialHeader refreshHeader = new MaterialHeader(context);                refreshHeader.setColorSchemeColors(ScOutdatedUtils.getColor(R.color.colorMain));                //关闭背景                refreshHeader.setShowBezierWave(false);                return refreshHeader;            }        });    }    public static RefWatcher getRefWatcher(Context context) {        MyApp application = (MyApp) context.getApplicationContext();        return application.refWatcher;    }    /**     * 获取ApplicationContext     *     * @return ApplicationContext     */    public static Context getContext() {        if (sContext != null) {            return sContext;        } else {            throw new NullPointerException("u should init first");        }    }    @Override    public void onCreate() {        super.onCreate();        sContext = getApplicationContext();        initLeakCanary();        initLogUtils(true, TAG);        initUtilCodeSpUtils(TAG);        JsonAnalysis.getInstance().setJsonType(JsonAnalysis.PARSER_TYPE_FAST_JSON);    }    private void initLeakCanary() {        if (LeakCanary.isInAnalyzerProcess(this)) {            // This process is dedicated to LeakCanary for heap analysis.            // You should not init your app in this process.            return;        }        refWatcher  = LeakCanary.install(this);    }}