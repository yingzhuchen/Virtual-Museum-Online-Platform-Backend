package org.openapitools.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AudioResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-31T10:49:10.951+08:00[Asia/Shanghai]")

public class AudioResponse   {
  @JsonProperty("audioUrl")
  private String audioUrl;

  public AudioResponse audioUrl(String audioUrl) {
    this.audioUrl = audioUrl;
    return this;
  }

  /**
   * 返还音频url，通常应该是cacheUrl
   * @return audioUrl
  */
  @ApiModelProperty(required = true, value = "返还音频url，通常应该是cacheUrl")
  @NotNull


  public String getAudioUrl() {
    return audioUrl;
  }

  public void setAudioUrl(String audioUrl) {
    this.audioUrl = audioUrl;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AudioResponse audioResponse = (AudioResponse) o;
    return Objects.equals(this.audioUrl, audioResponse.audioUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(audioUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AudioResponse {\n");
    
    sb.append("    audioUrl: ").append(toIndentedString(audioUrl)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

