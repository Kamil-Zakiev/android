package com.example.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final private ExpressionBuilder _exprBuilder = new ExpressionBuilder(new CalculatorImpl());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TextView resultPreviewDisplay = findViewById(R.id.display);
        final TextView exprDisplay = findViewById(R.id.expression_display);

        AddClickListenerFor(exprDisplay, resultPreviewDisplay, R.id.button0, '0');
        AddClickListenerFor(exprDisplay, resultPreviewDisplay, R.id.button1, '1');
        AddClickListenerFor(exprDisplay, resultPreviewDisplay, R.id.button2, '2');
        AddClickListenerFor(exprDisplay, resultPreviewDisplay, R.id.button3, '3');
        AddClickListenerFor(exprDisplay, resultPreviewDisplay, R.id.button4, '4');
        AddClickListenerFor(exprDisplay, resultPreviewDisplay, R.id.button5, '5');
        AddClickListenerFor(exprDisplay, resultPreviewDisplay, R.id.button6, '6');
        AddClickListenerFor(exprDisplay, resultPreviewDisplay, R.id.button7, '7');
        AddClickListenerFor(exprDisplay, resultPreviewDisplay, R.id.button8, '8');
        AddClickListenerFor(exprDisplay, resultPreviewDisplay, R.id.button9, '9');

        findViewById(R.id.button_plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _exprBuilder.Append('+');
                UpdateView(exprDisplay, resultPreviewDisplay);
            }
        });

        findViewById(R.id.button_minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _exprBuilder.Append('-');
                UpdateView(exprDisplay, resultPreviewDisplay);
            }
        });

        Button equalButton = findViewById(R.id.button_launch);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _exprBuilder.ReduceExpression();
                UpdateView(exprDisplay, resultPreviewDisplay);
            }
        });


        findViewById(R.id.kitty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _exprBuilder.Reset();
                UpdateView(exprDisplay, resultPreviewDisplay);
            }
        });
    }

    private void AddClickListenerFor(final TextView exprDisplay, final TextView resultPreviewDisplay, int viewId, final char value) {
        findViewById(viewId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _exprBuilder.Append(value);
                UpdateView(exprDisplay, resultPreviewDisplay);
            }
        });
    }

    private void UpdateView(final TextView exprDisplay, final TextView resultPreviewDisplay) {
        exprDisplay.setText(_exprBuilder.GetExpression());
        resultPreviewDisplay.setText(_exprBuilder.GetCalculationPreview());
    }
}

