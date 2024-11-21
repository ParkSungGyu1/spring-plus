package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.TodoSearchResponseDto;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    QTodo todo = QTodo.todo;
    QUser user = QUser.user;
    QComment comment = QComment.comment;
    QManager manager = QManager.manager;

    @Override
    public Optional<Todo> findByIdWithUserDSL(Long todoId) {
        QTodo todo = QTodo.todo;
        Todo result = queryFactory
                .selectFrom(todo)
                .leftJoin(todo.user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<TodoSearchResponseDto> findByDynamicQuery(int page, int size,String title, String startDate, String endDate, String nickName) {

        BooleanBuilder builder = new BooleanBuilder();

        // 제목 검색
        if (title != null && !title.isEmpty()) {
            builder.and(todo.title.containsIgnoreCase(title));
        }

        // 생성일 범위 검색
        if (startDate != null && !startDate.isEmpty()) {
            builder.and(todo.createdAt.goe(LocalDate.parse(startDate).atStartOfDay()));
        }
        if (endDate != null && !endDate.isEmpty()) {
            builder.and(todo.createdAt.loe(LocalDate.parse(endDate).atTime(LocalTime.MAX)));
        }

        // 담당자 닉네임 검색
        if (nickName != null && !nickName.isEmpty()) {
            builder.and(manager.user.nickName.containsIgnoreCase(nickName));
        }

        // 쿼리 실행
        return queryFactory.select(Projections.constructor(TodoSearchResponseDto.class,
                        todo.title,
                        manager.countDistinct().as("managerCount"),
                        comment.id.countDistinct().as("commentCount")))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .leftJoin(todo.comments, comment)
                .where(
                        eqTitle(title),
                        eqNickName(nickName)
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset((long) page * size)
                .limit(size)
                .fetch();
    }

    private BooleanExpression eqNickName(String nickName){
        if(nickName == null || nickName.isEmpty()){
            return null;
        }
        return user.nickName.eq(nickName);
    }

    private BooleanExpression eqTitle(String title){
        if(title == null || title.isEmpty()){
            return null;
        }
        return todo.title.eq(title);
    }

}
