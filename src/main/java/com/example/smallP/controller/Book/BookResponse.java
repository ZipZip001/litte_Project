package com.example.smallP.controller.Book;

public class BookResponse<T>{
    private T result;
    private Meta meta;

    public T getResult() {
        return result;
    }
    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public void setResult(T result) {
        this.result = result;
    }

    // phân trang
    public static class Meta {
        private int current;
        private int pageSize;
        private int pages;
        private int total;

        //defalut constructor
        public  Meta(){

        }

        public Meta(int current, int pageSize, int pages, int total) {
            this.current = current;
            this.pageSize = pageSize;
            this.pages = pages;
            this.total = total;
        }



        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
