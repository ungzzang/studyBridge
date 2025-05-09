package com.green.acamatch.entity.review;

import com.green.acamatch.entity.datetime.UpdatedAt;
import com.green.acamatch.entity.joinClass.JoinClass;
import com.green.acamatch.entity.myenum.UserRole;
import com.green.acamatch.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "review", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"join_class_id", "user_id"})
})
public class Review extends UpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private int star;

    @Column(length = 5000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "join_class_id", nullable = false)
    private JoinClass joinClass;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "ban_review", nullable = false)
    private int banReview = 0; // 기본값 0으로 설정
}
