package dev.baofeng.com.supermovie;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.List;

/**
 * Created by oceanzhang on 2016/10/28.
 */

public class DelegateApplicationPackageManager extends PackageManager {
    private static final String TAG = "DelegateApplicationPack";
    private static final String realPackageName = "dev.baofeng.com.supermovie";
    PackageManager packageManager;
    public DelegateApplicationPackageManager(PackageManager packageManager) {
        this.packageManager = packageManager;
    }

    @Override
    public PackageInfo getPackageInfo(String packageName, int flags)
            throws NameNotFoundException {
        Log.w(TAG, "getPackageInfo() :" + packageName);
        PackageInfo pi = packageManager.getPackageInfo(realPackageName, flags);
        pi.applicationInfo.packageName = packageName;
        pi.packageName = packageName;
        return pi;
    }

    @Override
    public String[] currentToCanonicalPackageNames(String[] names) {
        return packageManager.currentToCanonicalPackageNames(names);
    }

    @Override
    public String[] canonicalToCurrentPackageNames(String[] names) {
        return packageManager.canonicalToCurrentPackageNames(names);
    }

    @Override
    public Intent getLaunchIntentForPackage(String packageName) {
        Log.w(TAG, "getLaunchIntentForPackage() ");
        return packageManager.getLaunchIntentForPackage(packageName);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Intent getLeanbackLaunchIntentForPackage(String packageName) {
        Log.w(TAG, "getLeanbackLaunchIntentForPackage() ");
        return packageManager.getLeanbackLaunchIntentForPackage(packageName);
    }

    @Override
    public int[] getPackageGids(String packageName) throws NameNotFoundException {
        Log.w(TAG, "getPackageGids() ");
        return getPackageGids(packageName, 0);
    }

    @Override
    public int[] getPackageGids(String packageName, int flags)
            throws NameNotFoundException {
        Log.w(TAG, "getPackageGids() ");
        return getPackageGids(packageName,flags);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getPackageUid(String packageName, int flags) throws NameNotFoundException {
        Log.w(TAG, "getPackageUid() ");
        return packageManager.getPackageUid(packageName,flags);
    }

    @Override
    public PermissionInfo getPermissionInfo(String name, int flags)
            throws NameNotFoundException {
        Log.w(TAG, "getPermissionInfo() ");
        return packageManager.getPermissionInfo(name,flags);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PermissionInfo> queryPermissionsByGroup(String group, int flags)
            throws NameNotFoundException {
        return packageManager.queryPermissionsByGroup(group,flags);
    }

    @Override
    public PermissionGroupInfo getPermissionGroupInfo(String name,
                                                      int flags) throws NameNotFoundException {
        return packageManager.getPermissionGroupInfo(name,flags);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
        return packageManager.getAllPermissionGroups(flags);
    }

    @Override
    public ApplicationInfo getApplicationInfo(String packageName, int flags)
            throws NameNotFoundException {
        Log.w(TAG, "getApplicationInfo() ");
        if("com.xunlei.downloadprovider".equals(packageName)) {
            packageName = realPackageName;
        }
        ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, flags);
        Bundle metaData = applicationInfo.metaData;
        if(metaData == null) {
            metaData = new Bundle();
            applicationInfo.metaData = metaData;
        }
        metaData.putString("com.xunlei.download.APP_KEY","bpIzNjAxNTsxNTA0MDk0ODg4LjQyODAwMAOxNw==^a2cec7^10e7f1756b15519e20ffb6cf0fbf671f");
        return applicationInfo;
    }

    @Override
    public ActivityInfo getActivityInfo(ComponentName className, int flags)
            throws NameNotFoundException {
        Log.w(TAG, "getActivityInfo() " + className.getClassName());
        return packageManager.getActivityInfo(className, flags);
    }

    @Override
    public ActivityInfo getReceiverInfo(ComponentName className, int flags)
            throws NameNotFoundException {
        return packageManager.getReceiverInfo(className, flags);
    }

    @Override
    public ServiceInfo getServiceInfo(ComponentName className, int flags)
            throws NameNotFoundException {
        return packageManager.getServiceInfo(className,flags);
    }

    @Override
    public ProviderInfo getProviderInfo(ComponentName className, int flags)
            throws NameNotFoundException {
        return packageManager.getProviderInfo(className, flags);
    }

    @Override
    public String[] getSystemSharedLibraryNames() {
        return packageManager.getSystemSharedLibraryNames();
    }


    @Override
    @SuppressWarnings("unchecked")
    public FeatureInfo[] getSystemAvailableFeatures() {
        return packageManager.getSystemAvailableFeatures();
    }

    @Override
    public boolean hasSystemFeature(String name) {
        return packageManager.hasSystemFeature(name);
    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean hasSystemFeature(String name, int version) {
        return packageManager.hasSystemFeature(name, version);
    }

    @Override
    public int checkPermission(String permName, String pkgName) { //TODO packagename
        return packageManager.checkPermission(permName, pkgName);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean isPermissionRevokedByPolicy(String permName, String pkgName) {
        Log.w(TAG, "isPermissionRevokedByPolicy() ");
        return packageManager.isPermissionRevokedByPolicy(permName,pkgName);
    }

    @Override
    public boolean addPermission(PermissionInfo info) {
        return packageManager.addPermission(info);
    }

    @Override
    public boolean addPermissionAsync(PermissionInfo info) {
        return packageManager.addPermissionAsync(info);
    }

    @Override
    public void removePermission(String name) {
        packageManager.removePermission(name);
    }

    @Override
    public int checkSignatures(String pkg1, String pkg2) {
        return packageManager.checkSignatures(pkg1, pkg2);
    }

    @Override
    public int checkSignatures(int uid1, int uid2) {
        return packageManager.checkSignatures(uid1, uid2);
    }

    @Override
    public String[] getPackagesForUid(int uid) {
        return packageManager.getPackagesForUid(uid);
    }

    @Override
    public String getNameForUid(int uid) {
        return packageManager.getNameForUid(uid);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PackageInfo> getInstalledPackages(int flags) {
        return packageManager.getInstalledPackages(flags);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressWarnings("unchecked")
    @Override
    public List<PackageInfo> getPackagesHoldingPermissions(
            String[] permissions, int flags) {
        return packageManager.getPackagesHoldingPermissions(permissions,flags);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ApplicationInfo> getInstalledApplications(int flags) {
        return packageManager.getInstalledApplications(flags);
    }

    @Override
    public ResolveInfo resolveActivity(Intent intent, int flags) {
        ComponentName componentName = intent.getComponent();
        Log.d(TAG,"resolveActivity" + componentName.getClassName());
        intent.setComponent(new ComponentName(realPackageName,componentName.getClassName()));
        intent.setPackage(realPackageName);
        return packageManager.resolveActivity(intent,flags);
    }

    @Override
    public List<ResolveInfo> queryIntentActivities(Intent intent,
                                                   int flags) {
        return packageManager.queryIntentActivities(intent,flags);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ResolveInfo> queryIntentActivityOptions(
            ComponentName caller, Intent[] specifics, Intent intent,
            int flags) {
        return packageManager.queryIntentActivityOptions(caller,specifics,intent,flags);
    }

    @Override
    public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int flags) {
        return packageManager.queryBroadcastReceivers(intent, flags);
    }

    @Override
    public ResolveInfo resolveService(Intent intent, int flags) {
        ComponentName componentName = intent.getComponent();
        Log.d(TAG,"resolveService" + componentName.getClassName());
        intent.setComponent(new ComponentName(realPackageName,componentName.getClassName()));
        intent.setPackage(realPackageName);
        return packageManager.resolveService(intent, flags);
    }

    @Override
    public List<ResolveInfo> queryIntentServices(Intent intent, int flags) {
        return packageManager.queryIntentServices(intent, flags);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public List<ResolveInfo> queryIntentContentProviders(Intent intent, int flags) {
        return packageManager.queryIntentContentProviders(intent, flags);
    }

    @Override
    public ProviderInfo resolveContentProvider(String name, int flags) {
        return packageManager.resolveContentProvider(name, flags);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<ProviderInfo> queryContentProviders(String processName,
                                                    int uid, int flags) {
        return packageManager.queryContentProviders(processName, uid, flags);
    }

    @Override
    public InstrumentationInfo getInstrumentationInfo(
            ComponentName className, int flags)
            throws NameNotFoundException {
        return packageManager.getInstrumentationInfo(className, flags);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<InstrumentationInfo> queryInstrumentation(
            String targetPackage, int flags) {
        return packageManager.queryInstrumentation(targetPackage, flags);
    }

    @Override
    public Drawable getDrawable(String packageName, int resId,
                                ApplicationInfo appInfo) {
        Log.w(TAG, "getDrawable() ");
        return packageManager.getDrawable(packageName, resId, appInfo);
    }

    @Override public Drawable getActivityIcon(ComponentName activityName)
            throws NameNotFoundException {
        return packageManager.getActivityIcon(activityName);
    }

    @Override public Drawable getActivityIcon(Intent intent)
            throws NameNotFoundException {
        if (intent.getComponent() != null) {
            return getActivityIcon(intent.getComponent());
        }

        ResolveInfo info = resolveActivity(
                intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (info != null) {
            return info.activityInfo.loadIcon(this);
        }

        throw new NameNotFoundException(intent.toUri(0));
    }

    @Override public Drawable getDefaultActivityIcon() {
        return packageManager.getDefaultActivityIcon();
    }

    @Override public Drawable getApplicationIcon(ApplicationInfo info) {
        return info.loadIcon(this);
    }

    @Override public Drawable getApplicationIcon(String packageName)
            throws NameNotFoundException {
        Log.w(TAG, "getApplicationIcon() ");
        return packageManager.getApplicationIcon(packageName);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public Drawable getActivityBanner(ComponentName activityName)
            throws NameNotFoundException {
        return packageManager.getActivityBanner(activityName);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public Drawable getActivityBanner(Intent intent)
            throws NameNotFoundException {
        return packageManager.getActivityBanner(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public Drawable getApplicationBanner(ApplicationInfo info) {
        return packageManager.getApplicationBanner(info);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public Drawable getApplicationBanner(String packageName)
            throws NameNotFoundException {
        Log.w(TAG, "getApplicationBanner() ");
        return packageManager.getApplicationBanner(packageName);
    }

    @Override
    public Drawable getActivityLogo(ComponentName activityName)
            throws NameNotFoundException {
        return packageManager.getActivityLogo(activityName);
    }

    @Override
    public Drawable getActivityLogo(Intent intent)
            throws NameNotFoundException {
        if (intent.getComponent() != null) {
            return getActivityLogo(intent.getComponent());
        }

        ResolveInfo info = resolveActivity(
                intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (info != null) {
            return info.activityInfo.loadLogo(this);
        }

        throw new NameNotFoundException(intent.toUri(0));
    }

    @Override
    public Drawable getApplicationLogo(ApplicationInfo info) {
        return info.loadLogo(this);
    }

    @Override
    public Drawable getApplicationLogo(String packageName)
            throws NameNotFoundException {
        Log.w(TAG, "getApplicationLogo() ");
        return packageManager.getApplicationLogo(packageName);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Drawable getUserBadgedIcon(Drawable icon, UserHandle user) {
        return packageManager.getUserBadgedIcon(icon,user);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Drawable getUserBadgedDrawableForDensity(Drawable drawable, UserHandle user,
                                                    Rect badgeLocation, int badgeDensity) {
        return packageManager.getUserBadgedDrawableForDensity(drawable,user,badgeLocation,badgeDensity);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public CharSequence getUserBadgedLabel(CharSequence label, UserHandle user) {
        return packageManager.getUserBadgedLabel(label,user);
    }

    @Override
    public Resources getResourcesForActivity(ComponentName activityName)
            throws NameNotFoundException {
        return packageManager.getResourcesForActivity(activityName);
    }

    @Override
    public Resources getResourcesForApplication(ApplicationInfo app)
            throws NameNotFoundException {
        return packageManager.getResourcesForApplication(app);
    }

    @Override
    public Resources getResourcesForApplication(String appPackageName)
            throws NameNotFoundException {
        Log.w(TAG, "getResourcesForApplication() ");
        return packageManager.getResourcesForApplication(appPackageName);
    }


    @Override
    public boolean isSafeMode() {
        return packageManager.isSafeMode();
    }


    @Override
    public CharSequence getText(String packageName, int resid,
                                ApplicationInfo appInfo) {
        Log.w(TAG, "getText() ");
        return packageManager.getText(packageName,resid,appInfo);
    }

    @Override
    public XmlResourceParser getXml(String packageName, int resid,
                                    ApplicationInfo appInfo) {
        Log.w(TAG, "getXml() ");
        return packageManager.getXml(packageName,resid,appInfo);
    }

    @Override
    public CharSequence getApplicationLabel(ApplicationInfo info) {
        Log.w(TAG, "getApplicationLabel() ");
        return packageManager.getApplicationLabel(info);
    }


    @Override
    public void verifyPendingInstall(int id, int response) {
        Log.w(TAG, "verifyPendingInstall() ");
        packageManager.verifyPendingInstall(id,response);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void extendVerificationTimeout(int id, int verificationCodeAtTimeout,
                                          long millisecondsToDelay) {
        Log.w(TAG, "extendVerificationTimeout() ");
        packageManager.extendVerificationTimeout(id,verificationCodeAtTimeout,millisecondsToDelay);
    }


    @Override
    public void setInstallerPackageName(String targetPackage,
                                        String installerPackageName) {
        Log.w(TAG, "setInstallerPackageName() ");
        packageManager.setInstallerPackageName(targetPackage,installerPackageName);
    }

    @Override
    public String getInstallerPackageName(String packageName) {
        Log.w(TAG, "getInstallerPackageName() ");
        return packageManager.getInstallerPackageName(packageName);
    }


    @Override
    public void addPackageToPreferred(String packageName) {
        Log.w(TAG, "addPackageToPreferred() ");
        packageManager.addPackageToPreferred(packageName);
    }

    @Override
    public void removePackageFromPreferred(String packageName) {
        Log.w(TAG, "removePackageFromPreferred() is a no-op");
        packageManager.removePackageFromPreferred(packageName);
    }

    @Override
    public List<PackageInfo> getPreferredPackages(int flags) {
        Log.w(TAG, "getPreferredPackages() is a no-op");
        return packageManager.getPreferredPackages(flags);
    }

    @Override
    public void addPreferredActivity(IntentFilter filter,
                                     int match, ComponentName[] set, ComponentName activity) {
        Log.w(TAG, "addPreferredActivity() ");
        packageManager.addPreferredActivity(filter, match, set, activity);
    }



    @Override
    public void clearPackagePreferredActivities(String packageName) {
        Log.w(TAG, "clearPackagePreferredActivities() ");
        packageManager.clearPackagePreferredActivities(packageName);
    }

    @Override
    public int getPreferredActivities(List<IntentFilter> outFilters,
                                      List<ComponentName> outActivities, String packageName) {
        Log.w(TAG, "getPreferredActivities() ");
        return packageManager.getPreferredActivities(outFilters,outActivities,packageName);
    }

    @Override
    public void setComponentEnabledSetting(ComponentName componentName,
                                           int newState, int flags) {
        packageManager.setComponentEnabledSetting(componentName,newState,flags);
    }

    @Override
    public int getComponentEnabledSetting(ComponentName componentName) {
        return packageManager.getComponentEnabledSetting(componentName);
    }

    @Override
    public void setApplicationEnabledSetting(String packageName,
                                             int newState, int flags) {
        Log.w(TAG, "setApplicationEnabledSetting() ");
        packageManager.setApplicationEnabledSetting(packageName,newState,flags);
    }

    @Override
    public int getApplicationEnabledSetting(String packageName) {
        Log.w(TAG, "getApplicationEnabledSetting() ");
        return packageManager.getApplicationEnabledSetting(packageName);
    }


    public boolean getApplicationHiddenSettingAsUser(String packageName, UserHandle user) {
        Log.w(TAG, "getApplicationHiddenSettingAsUser() ");
        return getApplicationHiddenSettingAsUser(packageName,user);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public PackageInstaller getPackageInstaller() {
        Log.w(TAG, "getPackageInstaller() ");
        return packageManager.getPackageInstaller();
    }

}
