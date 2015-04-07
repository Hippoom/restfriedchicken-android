package com.restfriedchicken.android.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.restfriedchicken.android.R;
import com.restfriedchicken.android.RestfriedChickenApp;
import com.restfriedchicken.rest.onlinetxn.OnlineTxnRepresentation;

public class MakePaymentActivity extends Activity {

    public static int REQUEST_CODE_MAKE_PAYMENT = 10058;

    private String link;
    private String amountDue;
    private EditText amountEdit;
    private EditText ccNumberEdit;
    private EditText ccExpireDateEdit;
    private EditText cvv2Edit;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);

        Intent intent = getIntent();

        this.amountDue = intent.getStringExtra("amount_due");
        this.link = intent.getStringExtra("make_payment_link");

        this.amountEdit = (EditText) findViewById(R.id.amount_to_pay_edit);
        this.amountEdit.setText(amountDue);

        this.ccNumberEdit = (EditText) findViewById(R.id.credit_card_number_edit);
        this.ccExpireDateEdit = (EditText) findViewById(R.id.expire_date_edit);
        this.cvv2Edit = (EditText) findViewById(R.id.cvv2_edit);

        this.submit = (Button) findViewById(R.id.button_make_payment);
        this.submit.setOnClickListener(new MakePaymentButtonListener(this));
    }

    protected String getLink() {
        return link;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.button_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    static class MakePaymentButtonListener implements View.OnClickListener {
        private MakePaymentActivity caller;

        MakePaymentButtonListener(MakePaymentActivity caller) {
            this.caller = caller;
        }

        @Override
        public void onClick(View v) {
            new MakePaymentTask((RestfriedChickenApp) caller.getApplication(), new MakePaymentCallback(caller), caller).execute();
        }
    }

    protected String getCreditCardCVV2() {
        return cvv2Edit.getText().toString();
    }

    protected String getCreditCardExpireDate() {
        return ccExpireDateEdit.getText().toString();
    }

    protected String getCreditCardNumber() {
        return ccNumberEdit.getText().toString();
    }

    protected String getAmount() {
        return amountEdit.getText().toString();
    }


    static class MakePaymentCallback extends GetCustomerResourceTask.UiCallback<OnlineTxnRepresentation> {

        private MakePaymentActivity caller;

        MakePaymentCallback(MakePaymentActivity caller) {
            this.caller = caller;
        }

        @Override
        public void handle(OnlineTxnRepresentation onlineTxnRepresentation) {
            Toast.makeText(caller.getApplicationContext(), "Transaction is submitted",
                    Toast.LENGTH_SHORT).show();
            caller.finish();
        }
    }
}
