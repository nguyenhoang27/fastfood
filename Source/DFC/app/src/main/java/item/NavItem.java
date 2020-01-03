package item;

public class NavItem
{
    public String mTitle;
    public String mSubtitle;
    public String mCategoryID;
    public int mIcon;

    public NavItem(String title, String subtitle, int icon, String CategoryID)
    {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
        mCategoryID = CategoryID;
    }
}
