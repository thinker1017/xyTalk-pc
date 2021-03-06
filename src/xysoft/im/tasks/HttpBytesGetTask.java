package xysoft.im.tasks;

import xysoft.im.utils.HttpUtil;

import java.io.IOException;


public class HttpBytesGetTask extends HttpTask
{
    @Override
    public void execute(String url)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    byte[] ret = HttpUtil.getBytes(url, headers, requestParams);
                    if (listener != null)
                    {
                        listener.onSuccess(ret);
                    }
                }
                catch (IOException e)
                {
                    if (listener != null)
                    {
                        listener.onFailed();
                    }
                }


            }
        }).start();
    }
}
