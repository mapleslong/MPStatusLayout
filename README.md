```java

 
 使用方法：需要在gradle中引用   compile 'com.mapleslong.widget:MPStatusLayout:2.2.3'
  
 /**
  * 可以在application中设置MPStatusLayoutConfig进行全局配置和更换状态图片
  * 以下是简单使用的方式
  */
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_EMPTY = 1;
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_NONETWORK = 3;
    public static final int STATUS_LOADING = 4;
    //简单使用只要填充这个
    mStatusLayout.setStatus(MPStatusLayout.STATUS_SUCCESS);
    //还可以进行重试的回调
      mStatusLayout.setStatus(MPStatusLayout.STATUS_NONETWORK, new MPStatusLayout.onReloadListener() {
                        @Override
                        public void onReLoad(View v) {
                            Toast.makeText(MainActivity.this, "按了重试", Toast.LENGTH_SHORT).show();
                        }
                    });
   
    
```