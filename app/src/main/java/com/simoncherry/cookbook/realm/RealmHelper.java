package com.simoncherry.cookbook.realm;

import com.simoncherry.cookbook.model.MobCategory;
import com.simoncherry.cookbook.model.RealmCategory;
import com.simoncherry.cookbook.model.RealmCollection;
import com.simoncherry.cookbook.model.RealmHistory;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Simon on 2017/4/4.
 */

public class RealmHelper {

    private final static String TAG = RealmHelper.class.getSimpleName();

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

    public static RealmCategory convertMobCategoryToRealmCategory(MobCategory mobCategory, boolean isChild) {
        RealmCategory realmCategory = new RealmCategory();
        realmCategory.setCtgId(mobCategory.getCtgId());
        realmCategory.setParentId(mobCategory.getParentId());
        realmCategory.setName(mobCategory.getName());
        realmCategory.setChild(isChild);
        return realmCategory;
    }

    public static MobCategory convertRealmCategoryToMobCategory(RealmCategory realmCategory) {
        MobCategory mobCategory = new MobCategory();
        mobCategory.setCtgId(realmCategory.getCtgId());
        mobCategory.setParentId(realmCategory.getParentId());
        mobCategory.setName(realmCategory.getName());
        return mobCategory;
    }

    public static void saveCategoryToRealm(Realm realm, List<RealmCategory> categoryList) {
        for (RealmCategory category : categoryList) {
            RealmResults<RealmCategory> realmResults = realm.where(RealmCategory.class)
                    .equalTo("ctgId", category.getCtgId()).findAll();
            if (realmResults.size() > 0) {
                RealmCategory old = realmResults.first();
                category.setSelected(old.isSelected());
            }
            realm.copyToRealmOrUpdate(category);
        }
    }

    public static void setFirstChildCategorySelected(Realm realm) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmCategory> results = realm.where(RealmCategory.class)
                        .equalTo("isChild", true)
                        .findAll();
                if (results.size() > 0) {
                    RealmCategory realmCategory = results.first();
                    realmCategory.setSelected(true);
                }
            }
        });
    }

    public static void changeCategorySelectedByCtgId(Realm realm, final String ctgId, final boolean isSelected) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmCategory> results = realm.where(RealmCategory.class)
                        .equalTo("ctgId", ctgId)
                        .findAll();
                if (results.size() > 0) {
                    RealmCategory realmCategory = results.first();
                    realmCategory.setSelected(isSelected);
                }
            }
        });
    }
}
