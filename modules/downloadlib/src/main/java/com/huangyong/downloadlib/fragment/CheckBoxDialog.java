package com.huangyong.downloadlib.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.huangyong.downloadlib.model.Params;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.TorrentFileInfo;
import com.xunlei.downloadlib.parameter.TorrentInfo;

import java.util.ArrayList;
import java.util.List;

class CheckBoxDialog {

    public static void showCheckBoxDialog(Context context, final TorrentInfo list, String filePath, final OnChoseFileListener listener){

        final List<Integer> listArr = new ArrayList();
        final List<String> listName= new ArrayList<>();
        final String[] name = new String[list.mSubFileInfo.length];
        final boolean[] isCheckedList = new boolean[list.mSubFileInfo.length];

        final List<Integer> choseIndex = new ArrayList();
        final List<String> choseName = new ArrayList();

        for (int j = 0; j <list.mSubFileInfo.length ; j++) {
            listArr.add(list.mSubFileInfo[j].mFileIndex);
            listName.add(list.mSubFileInfo[j].mFileName);
            name[j]= list.mSubFileInfo[j].mFileName;
            isCheckedList[j]=false;
        }
        try {
            XLTaskHelper.instance().addTorrentTask(filePath,Params.DEFAULT_PATH,listArr);
        } catch (Exception e) {
            e.printStackTrace();
        }



        final boolean checkedItems[] = {true, false, false, false, false, false, false, false, false, false};
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("选择要下载的文件")//设置对话框的标题
                .setMultiChoiceItems(name, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Log.e("choseafile",which+"++++"+list.mFileCount+list.torrentPath);
                        isCheckedList[which] = isChecked;
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("choseafile",which+"++++"+list.mFileCount+list.torrentPath);

                        for (int i = 0; i < isCheckedList.length; i++) {
                            if (isCheckedList[i]){
                                choseIndex.add(listArr.get(i));
                                choseName.add(listName.get(i));
                            }
                        }

                        listener.onDownLoadTask(choseIndex,choseName);
                        dialog.dismiss();
                    }

                }).create();

        dialog.show();

    }
    private OnChoseFileListener listener;
    public interface OnChoseFileListener{
        void onDownLoadTask(List<Integer> choseIndex, List<String> choseName);
    }
}
