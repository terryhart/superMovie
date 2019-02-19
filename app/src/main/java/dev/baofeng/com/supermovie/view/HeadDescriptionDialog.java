package dev.baofeng.com.supermovie.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import dev.baofeng.com.supermovie.R;

public class HeadDescriptionDialog extends Dialog {


    private String title;
    private String content;

    public HeadDescriptionDialog(Context context, String title, String string) {
        super(context, R.style.Dialog_Fullscreen);
        this.title = title;
        this.content = string;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_content_layout);
        TextView headTitle = findViewById(R.id.head_title);
        TextView headContent = findViewById(R.id.head_content);

        headTitle.setText(title);
        headContent.setText(content);
    }
}
