package fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import adapter.ProductsAdapter;
import helper.DatabaseHelper;
import helper.SetProductListHelper;
import hhhai0304.dfc.R;
import item.Products;
import item.ProductsListItem;

public abstract class BaseFragment extends Fragment
{
    int sortMode = 0;
    DatabaseHelper db;
    EditText edtSearch;
    ImageButton imbSort;
    ListView lvAllProducts;
    ProductsAdapter adapter;
    ArrayList<Products> aProducts;
    ArrayList<ProductsListItem> itemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_base, container,	false);
        lvAllProducts = (ListView) rootView.findViewById(R.id.lvAllProducts);
        edtSearch = (EditText) rootView.findViewById(R.id.edtSearch);
        imbSort = (ImageButton) rootView.findViewById(R.id.imbSort);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
        setProductsAdapter("");

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    setProductsAdapter(edtSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

        imbSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return rootView;
    }

    protected abstract String setCategory();

    protected void setProductsAdapter(String search)
    {
        String cot;
        boolean tangdan;

        if(sortMode == 0 || sortMode == 1)
            cot = "ProductName";
        else
            cot = "Price";

        if (sortMode == 0 || sortMode == 2)
            tangdan = true;
        else
            tangdan = false;

        lvAllProducts.setAdapter(null);
        db = new DatabaseHelper(getActivity());
        try
        {
            aProducts = db.getAllProducts(cot, tangdan, setCategory(), search);
            SetProductListHelper setup = new SetProductListHelper(aProducts);
            itemList = setup.getProductsListItem();
            adapter = new ProductsAdapter(getActivity(), itemList);
            lvAllProducts.setAdapter(adapter);
        }
        catch (NullPointerException e)
        {
            Toast.makeText(getActivity(), "Không có kết quả", Toast.LENGTH_LONG).show();
        }
    }

    protected void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] sort = {"TÊN A - Z","TÊN Z - A","GIÁ TĂNG DẦN","GIÁ GIẢM DẦN"};
        builder.setTitle("SẮP XẾP");
        builder.setItems(sort, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                sortMode = which;
                setProductsAdapter(edtSearch.getText().toString());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}