package app.caihan.myscframe.base;import com.caihan.scframe.framework.v1.BaseActivity;import butterknife.ButterKnife;/** * @author caihan * @date 2018/4/22 * @e-mail 93234929@qq.com * 维护者 */public abstract class BaseScActivity extends BaseActivity {    @Override    protected void butterKnifebind() {        super.butterKnifebind();        ButterKnife.bind(this);    }    @Override    public void setImmersion() {        super.setImmersion();    }}