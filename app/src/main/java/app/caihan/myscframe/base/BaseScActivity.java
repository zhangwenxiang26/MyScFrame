package app.caihan.myscframe.base;import android.support.v7.widget.Toolbar;import android.view.View;import android.widget.TextView;import com.caihan.scframe.framework.v1.BaseActivity;import app.caihan.myscframe.R;import butterknife.ButterKnife;/** * @author caihan * @date 2018/4/22 * @e-mail 93234929@qq.com * 维护者 */public abstract class BaseScActivity extends BaseActivity {    @Override    protected void butterKnifebind() {        super.butterKnifebind();        ButterKnife.bind(this);    }    @Override    public void setImmersion() {        super.setImmersion();    }    /**     * 封装标题     *     * @param toolbar     * @param title     */    protected void setBaseToolbarLayout(Toolbar toolbar, String title) {        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("下载");        toolbar.setNavigationOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View v) {                finish();            }        });    }}