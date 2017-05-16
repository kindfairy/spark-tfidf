package ru.spbu.apmath.st033672;


import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class ViewResponse {

    private int total_rows;
    private int offset;

    private List<ResponseElement> rows;

    private static class ResponseElement {

        private String id;
        private JsonElement key;
        private JsonElement value;

        public JsonElement getKey() {
            return key;
        }

        public JsonElement getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "ResponseElement{" +
                    "id='" + id + '\'' +
                    ", key=" + key +
                    ", value=" + value +
                    '}';
        }

        public ResponseElement() {
        }
    }

    public ViewResponse() {
    }

    @Override
    public String toString() {
        return "ViewResponse{" +
                "total_rows=" + total_rows +
                ", offset=" + offset +
                ", rows=" + rows +
                '}';
    }

    public <K, V> List<KeyValuePair<K, V>> getKeyValuePairs(Class<K> classOfKey, Class<V> classOfValue) {

        List<KeyValuePair<K, V>> resultList = new ArrayList<>();

        Gson gson = new Gson();

        for (ResponseElement responseElement : rows) {
            //System.out.println(responseElement.getKey().toString());
            //System.out.println(responseElement.getValue().toString());

            K key = gson.fromJson(responseElement.getKey(), classOfKey);
            //System.out.println(key.toString());
            V value = gson.fromJson(responseElement.getValue(), classOfValue);
            //System.out.println(value.toString());
            KeyValuePair<K, V> kvKeyValuePair = new KeyValuePair<K, V>(key, value);
            resultList.add(kvKeyValuePair);

        }
        return resultList;
    }

    public int getTotal_rows(){
        return total_rows;
    }

}
