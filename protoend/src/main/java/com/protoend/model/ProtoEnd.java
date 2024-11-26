package com.protoend.model;

import com.protoend.auth.model.AuthType;
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

    @Column(name = "url")
    private String url;

    @Column(name = "auth_type")
    private AuthType authType;

    @Lob
    @Column(name = "request_detail")
    private byte[] requestDetail;

    @Lob
    @Column(name = "auth")
    private byte[] auth;

    @Column(name = "status")
    private TestStatus status;

    @Column(name = "created_time")
    private long createdTime;

    @Column(name = "additional_properties")
    private byte[] additionalProperties;

}