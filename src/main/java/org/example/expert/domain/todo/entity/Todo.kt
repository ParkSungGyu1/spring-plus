package org.example.expert.domain.todo.entity;

import jakarta.persistence.*;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.common.entity.Timestamped
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.user.entity.User;

import kotlin.collections.ArrayList

@Entity
@Table(name = "todos")
class Todo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String,
    var contents: String,
    var weather: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @OneToMany(mappedBy = "todo", cascade = [CascadeType.REMOVE])
    var comments: MutableList<Comment> = ArrayList(),

    @OneToMany(mappedBy = "todo", cascade = [CascadeType.ALL])
    var managers: MutableList<Manager> = ArrayList()
) : Timestamped()

