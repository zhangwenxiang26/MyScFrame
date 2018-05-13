package com.caihan.myscframe.base;import android.view.View;import com.caihan.scframe.framework.v1.support.MvpPresenter;import com.caihan.scframe.framework.v1.support.MvpView;import com.caihan.scframe.framework.v1.support.mvp.fragment.U1CityMvpFragment;import butterknife.ButterKnife;import butterknife.Unbinder;/** * 基类 * * @author caihan * @date 2018/2/18 * @e-mail 93234929@qq.com * 维护者 */public abstract class BaseScMvpFragment        <V extends MvpView, P extends MvpPresenter<V>>        extends U1CityMvpFragment<V, P> {    Unbinder mUnbinder;    @Override    protected void butterKnifeBind(View rootView) {        mUnbinder = ButterKnife.bind(this, rootView);    }    @Override    protected void butterKnifeUnBind() {        super.butterKnifeUnBind();    }    @Override    public boolean openImmersion() {        return super.openImmersion();    }    @Override    public void setImmersion() {        super.setImmersion();    }    @Override    public void onDestroyView() {        super.onDestroyView();        mUnbinder.unbind();    }}