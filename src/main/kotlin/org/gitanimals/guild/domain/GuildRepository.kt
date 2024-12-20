package org.gitanimals.guild.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GuildRepository : JpaRepository<Guild, Long> {

    fun existsByTitle(title: String): Boolean

    @Query(
        """
            select g from Guild as g
            where g.id = :id
            and g.leader.userId = :leaderId
        """
    )
    fun findGuildByIdAndLeaderId(@Param("id") id: Long, @Param("leaderId") leaderId: Long): Guild?

    @Query(
        """
            select g from Guild as g
            join fetch g.members as m 
            where g.leader.userId = :userId or m.userId = :userId
        """
    )
    fun findAllGuildByUserIdWithMembers(@Param("userId") userId: String): List<Guild>
}
