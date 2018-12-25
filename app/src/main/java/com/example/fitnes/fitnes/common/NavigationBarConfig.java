package com.example.fitnes.fitnes.common;

import android.app.Activity;

import com.example.fitnes.fitnes.R;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import androidx.appcompat.widget.Toolbar;

public class NavigationBarConfig {

    private Activity context;
    private Toolbar toolbar;

    public NavigationBarConfig(Activity c, Toolbar tb) {
        this.context = c;
        this.toolbar = tb;
    }
    public void setToolBar(){

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(context.getString(R.string.nbar_personality));
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(1).withName(context.getString(R.string.exit));
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(context)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Dmitriy Volkov")
                                .withEmail("dm8205@yandex.ru")
                                .withIcon(GoogleMaterial.Icon.gmd_supervisor_account)
                )
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();
        Drawer drawer = new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(context)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        item2
                )
                .build();
    }
}
