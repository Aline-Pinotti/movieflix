package com.devsuperior.movieflix.projections;


public interface MovieProjection extends IdProjection<Long> {

    String getTitle();
    String getSubTitle();
    String getSynopsis();
    Integer getPublishedAt();
    String getImgUrl();
    Long getGenreId();
    String getGenreName(); 

}
