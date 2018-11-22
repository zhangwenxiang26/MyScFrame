package app.caihan.myscframe.base;import android.view.View;import com.caihan.scframe.framework.v1.BaseFragment;import com.squareup.leakcanary.RefWatcher;import app.caihan.myscframe.MyApp;import butterknife.ButterKnife;import butterknife.Unbinder;/** * @author caihan * @date 2018/2/19 * @e-mail 93234929@qq.com * 维护者 */public abstract class BaseScFragment extends BaseFragment {    Unbinder mUnbinder;    @Override    protected void butterKnifeBind(View rootView) {        mUnbinder = ButterKnife.bind(this, rootView);    }    @Override    protected void butterKnifeUnBind() {        super.butterKnifeUnBind();    }    @Override    public void setImmersion() {        super.setImmersion();    }    @Override    public void onDestroyView() {        super.onDestroyView();        mUnbinder.unbind();    }    @Override    public void onDestroy() {        super.onDestroy();        RefWatcher refWatcher = MyApp.getRefWatcher(getActivity());        refWatcher.watch(this);    }}