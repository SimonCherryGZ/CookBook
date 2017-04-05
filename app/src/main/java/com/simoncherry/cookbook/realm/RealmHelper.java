package com.simoncherry.cookbook.realm;

import com.simoncherry.cookbook.model.RealmMobRecipe;

import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Simon on 2017/4/4.
 */

public class RealmHelper {

    public static void createMobRecipe(Realm realm, final RealmMobRecipe realmMobRecipe) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number maxId = realm.where(RealmMobRecipe.class).max("id");
                AtomicLong primaryKeyValue = new AtomicLong(maxId == null ? 0 : maxId.longValue());
                realmMobRecipe.setId(primaryKeyValue.incrementAndGet());
                realm.copyToRealm(realmMobRecipe);
            }
        });
    }

    public static void deleteMobRecipeByMenuId(final Realm realm, final String menuId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmMobRecipe> results = retrieveMobRecipeByMenuId(realm, menuId);
                if (results.size() > 0) {
                    results.deleteAllFromRealm();
                }
            }
        });
    }

    public static void deleteMobRecipeByResult(Realm realm, final RealmResults realmResults) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults.deleteAllFromRealm();
            }
        });
    }

    public static RealmResults<RealmMobRecipe> retrieveMobRecipeByMenuId(Realm realm, String menuId) {
        return realm.where(RealmMobRecipe.class)
                .equalTo("menuId", menuId)
                .findAll();
    }
}
