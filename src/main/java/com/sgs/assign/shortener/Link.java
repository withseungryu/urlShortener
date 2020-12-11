package com.sgs.assign.shortener;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name = "link")
@Table
@NoArgsConstructor
public class Link implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "encoded")
    private String encoded;


    @Builder
    public Link(String url, String encoded) {
        this.url = url;
        this.encoded = encoded;
    }
//
//    public String getUrl(){
//        return this.url;
//    }


}

