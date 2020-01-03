package helper;

import android.os.Environment;

public class PHPUrl
{
    private static String host = "http://hohoanghai.name.vn/";

    public static String login = host + "dfc/php/login.php";
    public static String getversion = host + "dfc/php/getversion.php";
    public static String get_category = host + "dfc/php/get_category.php";
    public static String register = host + "dfc/php/register.php";
    public static String orders = host + "dfc/php/orders.php";
    public static String get_orders = host + "dfc/php/get_orders.php";
    public static String get_orders_detail = host + "dfc/php/get_orders_detail.php";
    public static String getallproducts = host + "dfc/php/getallproducts.php";
    public static String update_account = host + "dfc/php/update_account.php";
    public static String changepass = host + "dfc/php/changepass.php";

    public static String gcm_senderid = "686776422514";

    public static String apptemp = Environment.getExternalStorageDirectory() + "/DFCTemp";
    public static String productAvatarSavePath = apptemp + "/Images";
    public static String fileVersion = "version.txt";
    public static String productAvatar = host + "dfc/images/";
    public static String productAvataronDisk = "file://" + productAvatarSavePath + "/";
}