package cn.edu.dlpulyt.keshe.service.impl;

import cn.edu.dlpulyt.keshe.mapper.MessageMapper;
import cn.edu.dlpulyt.keshe.mapper.TeamMapper;
import cn.edu.dlpulyt.keshe.mapper.TeamMemberMapper;
import cn.edu.dlpulyt.keshe.mapper.UserMapper;
import cn.edu.dlpulyt.keshe.pojo.Message;
import cn.edu.dlpulyt.keshe.pojo.Team;
import cn.edu.dlpulyt.keshe.pojo.TeamMember;
import cn.edu.dlpulyt.keshe.pojo.User;
import cn.edu.dlpulyt.keshe.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service

public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private TeamMemberMapper teamMemberMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    // 创建队伍：创建队伍的同时会新建一个成员，即队长自己
    @Override
    @Transactional(rollbackFor = Exception.class) // 保证方法对数据库操作的原子性，要么全部成功，要么全部失败
    public int create(Team team) {
        User user = userMapper.getuserById(team.getLeaderId());
        team.setLeaderPhonenum(user.getPhoneNum());
        team.setLeaderName(user.getName());
        teamMapper.create(team);
        TeamMember teamMember = new TeamMember();
        teamMember.setUserId(team.getLeaderId());
        teamMember.setTeamId(team.getId());
        return teamMemberMapper.create(teamMember);

    }

    @Override
    public List<Team> list() {
        return teamMapper.list();
    }

    // 添加队伍
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addTeam(TeamMember teamMember) {
        Team team = teamMapper.getById(teamMember.getTeamId());
        if (team.getLeaderId().equals(teamMember.getUserId())){
            return 0;
        }
        User user = userMapper.getuserById(teamMember.getUserId());
        teamMemberMapper.checkInTeam(teamMember);
        if(teamMemberMapper.checkInTeam(teamMember)!=null){
            return 0;
        }
        int res = teamMemberMapper.create(teamMember);
        Message message = Message.builder()
                .message("已加入" + team.getTeamName())
                .readStatus(0+"")
                .receiverId(team.getLeaderId())
                .senderId(teamMember.getUserId())
                .senderName(user.getName())
                .build();
        messageMapper.create(message);
        return res;
    }

    @Override
    public List<Map<String, Object>> listByUserId(String userId) {
        return teamMemberMapper.listByUser(userId);
    }
}
