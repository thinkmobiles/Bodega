package com.thinkmobiles.bodega.fragments.envios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.db.DBManager;
import com.thinkmobiles.bodega.db.daogen.Customer;
import com.thinkmobiles.bodega.fragments.BaseFragment;

/**
 * Created by denis on 03.11.15.
 */
public class AddToEnviosFragment extends BaseFragment implements View.OnClickListener {

    private ItemWrapper mItem;
    private long mCustomerId;

    private static final String FIRST_BLOCK_VISIBLE_ARG = "first_block_visible_arg";
    private static final String CUSTOMER_ID_ARG = "customer_id_arg";

    // UI
    private RelativeLayout rlRootContainer, rlFirstContainer, rlSecondContainer;
    private Button btnCancel, btnAccept, btnGoToEnvio, btnContinue;
    private AutoCompleteTextView autoCompleteTextView;
    private CheckBox chbExpandSpinner;

    public static BaseFragment newInstance(ItemWrapper _parentItem) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_ITEM, _parentItem);
        BaseFragment fragment = new AddToEnviosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_to_envios);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItem = args.getParcelable(Constants.EXTRA_ITEM);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRedBackground();
        findUI();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        restoreSavedInstance(savedInstanceState);
        setListeners();
        initAutoCompleteTextView();
        configureDropDown();
    }

    private void restoreSavedInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (!savedInstanceState.getBoolean(FIRST_BLOCK_VISIBLE_ARG)) {
                toggleContainersVisibility();
            }
            mCustomerId = savedInstanceState.getLong(CUSTOMER_ID_ARG);
        }
    }

    private void findUI() {
        rlRootContainer = $(R.id.rlRootContainer_FATE);
        btnAccept = $(R.id.btnAceptar_FATE);
        btnCancel = $(R.id.btnCancelar_FATE);
        autoCompleteTextView = $(R.id.actvLocal_FATE);
        btnGoToEnvio = $(R.id.btnVerEnvio_FATE);
        btnContinue = $(R.id.btnContinuar_FATE);
        rlFirstContainer = $(R.id.rlFirstContainer_FATE);
        rlSecondContainer = $(R.id.rlSecondContainer_FATE);
        chbExpandSpinner = $(R.id.chbDropDown_FATE);
    }

    private void setListeners() {
        rlRootContainer.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        btnGoToEnvio.setOnClickListener(this);
        rlFirstContainer.setOnClickListener(this);
        autoCompleteTextView.setOnClickListener(this);
        autoCompleteTextView.setOnItemClickListener(onItemClickListener);
        autoCompleteTextView.addTextChangedListener(textWatcher);
        chbExpandSpinner.setOnCheckedChangeListener(dropDownListener);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            chbExpandSpinner.postDelayed(new Runnable() {
                @Override
                public void run() {
                    chbExpandSpinner.setChecked(autoCompleteTextView.isPopupShowing());
                }
            }, 100);
        }
    };

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            chbExpandSpinner.setChecked(false);
        }
    };

    private CompoundButton.OnCheckedChangeListener dropDownListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked)
                autoCompleteTextView.showDropDown();
            else
                autoCompleteTextView.dismissDropDown();
        }
    };

    private void initAutoCompleteTextView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        arrayAdapter.clear();
        arrayAdapter.addAll(DBManager.getInstance(getApplicationContext()).getCustomerNames());
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.requestFocus();
    }

    private void configureDropDown() {
        if (autoCompleteTextView.getAdapter().getCount() == 0)
            chbExpandSpinner.setEnabled(false);
        chbExpandSpinner.setChecked(false);
    }

    @Override
    public void onClick(View v) {
        chbExpandSpinner.setChecked(false);
        switch (v.getId()) {
            case R.id.rlRootContainer_FATE:
            case R.id.btnCancelar_FATE:
            case R.id.btnContinuar_FATE:
                mFragmentNavigator.popBackStack();
                break;
            case R.id.btnAceptar_FATE:
                addCustomer();
                break;
            case R.id.btnVerEnvio_FATE:
                goToEnvios();
                break;
        }
    }

    private void goToEnvios() {
        mFragmentNavigator.popBackStack();
        mFragmentNavigator.showFragment(EnviosProductsFragment.newInstance(mCustomerId));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FIRST_BLOCK_VISIBLE_ARG, rlFirstContainer.getVisibility() == View.VISIBLE);
        outState.putLong(CUSTOMER_ID_ARG, mCustomerId);
    }

    private void addCustomer() {
        String customerName = autoCompleteTextView.getText().toString();
        if (!customerName.isEmpty()) {
            DBManager dbManager = DBManager.getInstance(getApplicationContext());
            Customer customer = dbManager.addCustomer(customerName);
            dbManager.addOrder(mItem, customer);
            mCustomerId = customer.getId();
            toggleContainersVisibility();
        } else {
            Toast.makeText(getActivity(), getString(R.string.enter_shop), Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleContainersVisibility() {
        rlFirstContainer.setVisibility(View.GONE);
        rlSecondContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        autoCompleteTextView.removeTextChangedListener(textWatcher);
    }
}
