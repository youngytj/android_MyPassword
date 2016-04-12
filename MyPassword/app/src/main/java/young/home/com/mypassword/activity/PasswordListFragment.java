package young.home.com.mypassword.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import young.home.com.mypassword.R;
import young.home.com.mypassword.adapter.PasswordListAdapter;
import young.home.com.mypassword.application.BaseFragment;
import young.home.com.mypassword.model.Password;
import young.home.com.mypassword.model.SettingKey;
import young.home.com.mypassword.service.MainBinder;
import young.home.com.mypassword.service.OnGetAllPasswordCallback;
import young.home.com.mypassword.service.OnPasswordChangeListener;
import young.home.com.mypassword.service.OnSettingChangeListener;

/**
 * Created by YOUNG on 2016/4/9.
 */
public class PasswordListFragment extends BaseFragment implements OnGetAllPasswordCallback,
        android.view.View.OnClickListener {

    /** 数据 */
    private PasswordListAdapter mainAdapter;

    /** 数据源 */
    private MainBinder mainBinder;

    private ListView listView;
    /** 没有数据的提示框 */
    private View noDataView;

    private String passwordGroupName;
    private OnPasswordChangeListener onPasswordListener = new OnPasswordChangeListener() {
        @Override
        public void onNewPassword(Password password) {
            if (password.getGroupName().equals(passwordGroupName)) {
                mainAdapter.onNewPassword(password);
                initView();
            }
        }

        @Override
        public void onDeletePassword(int id) {
            mainAdapter.onDeletePassword(id);
            initView();
        }

        @Override
        public void onUpdatePassword(Password newPassword) {
            mainAdapter.onUpdatePassword(newPassword);
            initView();
        }
    };

    public void setDataSource(MainBinder mainBinder) {
        this.mainBinder = mainBinder;
    }

    public void showPasswordGroup(String passwordGroupName) {
        this.passwordGroupName = passwordGroupName;
        mainBinder.getAllPassword(this, passwordGroupName);
    }

    public String getPasswordGroupName() {
        return passwordGroupName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainAdapter = new PasswordListAdapter(getActivity());
        mainBinder.registOnPasswordListener(onPasswordListener);
        showPasswordGroup(getBaseActivity().getSetting(SettingKey.LAST_SHOW_PASSWORDGROUP_NAME,
                getString(R.string.password_group_default_name)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregistOnPasswordListener();
    }

    private void unregistOnPasswordListener() {
        if (mainBinder != null) {
            mainBinder.unregistOnPasswordListener(onPasswordListener);
            mainBinder = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_password_list, container, false);
        listView = (ListView) rootView.findViewById(R.id.main_listview);
        listView.setAdapter(mainAdapter);

        noDataView = rootView.findViewById(R.id.main_no_passsword);
        noDataView.setOnClickListener(this);
        if (mainBinder == null) {
            noDataView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        } else {
            initView();
        }

        return rootView;
    }

    private void initView() {
        if (noDataView != null) {
            if (mainAdapter.getCount() == 0) {
                noDataView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            } else {
                noDataView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listView = null;
        noDataView = null;
    }

    @Override
    public void onGetAllPassword(String groupName, List<Password> passwords) {
        if (passwordGroupName.equals(groupName)) {
            mainAdapter.setPasswordGroup(passwordGroupName);
            mainAdapter.setData(passwords, mainBinder);
            initView();
            if (listView != null)
                listView.setSelection(0);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_no_passsword:
                Intent intent = new Intent(getActivity(), EditPasswordActivity.class);
                intent.putExtra(EditPasswordActivity.PASSWORD_GROUP, passwordGroupName);
                getActivity().startActivity(intent);
                break;
            default:
                break;
        }
    }
}