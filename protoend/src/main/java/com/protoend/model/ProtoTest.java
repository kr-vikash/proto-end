package com.protoend.model;

import com.protoend.model.enumerator.ConnectionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "connection")
@Getter
@Setter
public class ProtoTest {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private ConnectionType connectionType;

    @Column
    private String url;

    @Lob
    private byte[] requestDetail;
}