package com.protoend.model;

import com.protoend.base.model.enumerator.AuthType;
import com.protoend.base.model.enumerator.ConnectionType;
import com.protoend.base.model.enumerator.TestStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "connection")
@Getter
@Setter
public class ProtoEnd {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private ConnectionType connectionType;

    @Column
    private String url;

    @Column
    private AuthType authType;

    @Lob
    private byte[] requestDetail;

    @Lob
    private byte[] auth;

    @Column
    private TestStatus status;

    @Column
    private long createdTime;

    @Column
    private byte[] additionalProperties;

}