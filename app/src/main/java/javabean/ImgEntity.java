package javabean;

import java.util.List;

/**
 * Created by messi.mo on 2017/5/10.
 */

public class ImgEntity {
  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  private String imageUrl;

  public ImgEntity(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
