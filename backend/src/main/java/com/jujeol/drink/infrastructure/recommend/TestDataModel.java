package com.jujeol.drink.infrastructure.recommend;

import static java.util.stream.Collectors.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveArrayIterator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;

public class TestDataModel implements DataModel {

    private final List<Preference> userItems;

    public TestDataModel(List<Preference> userItems) {
        this.userItems = userItems;
    }

    @Override
    public LongPrimitiveIterator getUserIDs() {
        final LongPrimitiveArrayIterator result = new LongPrimitiveArrayIterator(
                userItems.stream()
                        .mapToLong(Preference::getUserID)
                        .distinct()
                        .toArray());
        return result;
    }

    @Override
    public int getNumItems() {
        final int count = (int) userItems.stream()
                .mapToLong(Preference::getItemID)
                .distinct()
                .count();
        return count;
    }

    @Override
    public int getNumUsers() {
        final int result = (int) userItems.stream()
                .mapToLong(Preference::getUserID)
                .distinct()
                .count();
        return result;
    }

    @Override
    public float getMaxPreference() {
        final float result = (float) userItems.stream().mapToDouble(Preference::getValue)
                .max().orElse(0.0);
        return result;
    }

    @Override
    public float getMinPreference() {
        final float result = (float) userItems.stream().mapToDouble(Preference::getValue)
                .filter(num -> num > 0.0)
                .min().orElse(0.0);
        return result;
    }







    @Override
    public PreferenceArray getPreferencesFromUser(long userID) {
        return new GenericUserPreferenceArray(userItems.stream()
                .filter(userItem -> userItem.getUserID() == userID)
                .collect(toList()));
    }

    @Override
    public FastIDSet getItemIDsFromUser(long userID) {
        return new FastIDSet(userItems.stream()
                .filter(userItem -> userItem.getUserID() == userID)
                .mapToLong(Preference::getItemID)
                .distinct()
                .toArray());
    }

    @Override
    public LongPrimitiveIterator getItemIDs() {
        return new LongPrimitiveArrayIterator(userItems.stream()
                .mapToLong(Preference::getItemID)
                .distinct()
                .toArray());
    }

    @Override
    public PreferenceArray getPreferencesForItem(long itemID) {
        return new GenericUserPreferenceArray(userItems.stream()
                .filter(userItem -> userItem.getItemID() == itemID)
                .collect(toList()));
    }

    @Override
    public Float getPreferenceValue(long userID, long itemID) {
        return userItems.stream()
                .filter(userItem -> userItem.getItemID() == itemID && userItem.getUserID() == userID)
                .map(Preference::getValue)
                .findAny()
                .orElse((float) 0);
    }

    @Override
    public Long getPreferenceTime(long userID, long itemID) throws TasteException {
        return null;
    }

    @Override
    public int getNumUsersWithPreferenceFor(long itemID) {
        return (int) userItems.stream().collect(Collectors.groupingBy(Preference::getUserID)).values()
                .stream()
                .filter(list -> list.stream().anyMatch(i -> i.getItemID() == itemID))
                .count();
    }

    @Override
    public int getNumUsersWithPreferenceFor(long itemID1, long itemID2) throws TasteException {
        long count = userItems.stream().collect(Collectors.groupingBy(Preference::getUserID)).values()
                .stream()
                .filter(list -> list.stream().anyMatch(i -> i.getItemID() == itemID1) &&
                        list.stream().anyMatch(i -> i.getItemID() == itemID2))
                .count();
        return (int) count;
    }

    @Override
    public void setPreference(long userID, long itemID, float value) throws TasteException {
        userItems.stream().filter(userItem -> (userItem.getUserID() == userID && userItem.getItemID() == itemID))
                .findAny().ifPresent(item -> item.setValue(value));
    }

    @Override
    public void removePreference(long userID, long itemID) {
        userItems.removeIf(userItem -> userItem.getItemID() == itemID && userItem.getUserID() == userID);
    }

    @Override
    public boolean hasPreferenceValues() {
        return true;
    }

    @Override
    public void refresh(Collection<Refreshable> alreadyRefreshed) {
        System.out.println("hello");
    }
}
