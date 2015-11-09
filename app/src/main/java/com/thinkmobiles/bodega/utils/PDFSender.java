package com.thinkmobiles.bodega.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.widget.Toast;

import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.db.DBManager;
import com.thinkmobiles.bodega.db.daogen.OrderItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PDFSender {

    public static void sendPDFsFromEnvio(Activity activity, long customerId) {

        List<OrderItem> items = DBManager.getInstance(activity.getApplicationContext()).getOrders(customerId);
        ArrayList<Uri> uris = new ArrayList<>();

        /*for (OrderItem orderItem : items) {
            File file = new File(ApiManager.getPath(activity) + orderItem.getPdf());
            uris.add(Uri.fromFile(file));
        }*/

        boolean isCopied;
        for (OrderItem orderItem : items) {
            isCopied = false;
            File file = new File(ApiManager.getPath(activity) + orderItem.getPdf());
            File newFile = new File(activity.getExternalFilesDir(null).getAbsolutePath() + File.separator + file.getName());
            try {
                if (file.exists()) {
                    copy(file, newFile);
                    isCopied = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isCopied) {
                uris.add(Uri.fromFile(newFile));
            }
        }

        Intent mailer = new Intent(Intent.ACTION_SEND_MULTIPLE);
        //mailer.putExtra(Intent.EXTRA_EMAIL, activity.getString(R.string.email));
        mailer.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.mail_topic_envio));
        mailer.setType("message/rfc822");
        mailer.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.mail_message_envio));
        mailer.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        try {
            activity.startActivity(Intent.createChooser(mailer, activity.getString(R.string.send_mail)));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity, activity.getString(R.string.error_send_email), Toast.LENGTH_SHORT).show();
        }
    }

    private static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

}
