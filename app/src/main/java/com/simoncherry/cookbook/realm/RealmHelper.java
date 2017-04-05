package com.simoncherry.cookbook.realm;

import com.simoncherry.cookbook.model.RealmCollection;
import com.simoncherry.cookbook.model.RealmHistory;

import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Simon on 2017/4/4.
 */

public class RealmHelper {

    public static void createCollection(Realm realm, final RealmCollection realmCollection) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number maxId = realm.where(RealmCollection.class).max("id");
                AtomicLong primaryKeyValue = new AtomicLong(maxId == null ? 0 : maxId.longValue());
                realmCollection.setId(primaryKeyValue.incrementAndGet());
                realm.copyToRealm(realmCollection);
            }
        });
    }

    public static RealmResults<RealmCollection> retrieveCollectionByMenuId(Realm realm, String menuId) {
        return realm.where(RealmCollection.class)
                .equalTo("menuId", menuId)
                .findAll();
    }

    public static void deleteCollectionByMenuId(final Realm realm, final String menuId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmCollection> results = retrieveCollectionByMenuId(realm, menuId);
                if (results.size() > 0) {
                    results.deleteAllFromRealm();
                }
            }
        });
    }

    public static void deleteCollectionByResult(Realm realm, final RealmResults realmResults) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmResults.deleteAllFromRealm();
            }
        });
    }

    public static void createHistory(Realm realm, final RealmHistory realmHistory) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number maxId = realm.where(RealmHistory.class).max("id");
                AtomicLong primaryKeyValue = new AtomicLong(maxId == null ? 0 : maxId.longValue());
                realmHistory.setId(primaryKeyValue.incrementAndGet());
                realm.copyToRealm(realmHistory);
            }
        });
    }

    public static RealmResults<RealmHistory> retrieveHistory(Realm realm) {
        return realm.where(RealmHistory.class)
                .findAll();
    }

    public static RealmResults<RealmHistory> retrieveHistoryByMenuId(Realm realm, String menuId) {
        return realm.where(RealmHistory.class)
                .equalTo("menuId", menuId)
                .findAll();
    }

    public static void deleteFirstHistory(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmHistory> results = realm.where(RealmHistory.class)
                        .findAll()
                        .sort("createTime", Sort.ASCENDING);
                if (results.size() > 0) {
                    results.deleteFirstFromRealm();
                }
            }
        });
    }

    public static void deleteHistoryByMenuId(final Realm realm, final String menuId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmHistory> results = retrieveHistoryByMenuId(realm, menuId);
                if (results.size() > 0) {
                    results.deleteAllFromRealm();
                }
            }
        });
    }
}
