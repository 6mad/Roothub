package wang.miansen.roothub.modules.follow.controller.front;

import java.util.Date;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import wang.miansen.roothub.common.beans.BaseEntity;
import wang.miansen.roothub.common.beans.Page;
import wang.miansen.roothub.common.beans.Result;
import wang.miansen.roothub.common.controller.AbstractBaseController;
import wang.miansen.roothub.common.controller.SessionController;
import wang.miansen.roothub.common.dao.mapper.wrapper.query.QueryWrapper;
import wang.miansen.roothub.common.service.BaseService;
import wang.miansen.roothub.common.util.DateUtils;
import wang.miansen.roothub.modules.follow.service.FollowService;
import wang.miansen.roothub.modules.follow.vo.FollowVO;
import wang.miansen.roothub.modules.user.dto.UserDTO;
import wang.miansen.roothub.modules.user.model.User;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import wang.miansen.roothub.modules.follow.dto.FollowDTO;
import wang.miansen.roothub.modules.follow.model.Follow;
import wang.miansen.roothub.modules.collect.dto.CollectDTO;
import wang.miansen.roothub.modules.collect.service.CollectService;
import wang.miansen.roothub.modules.collect.vo.CollectVO;
import wang.miansen.roothub.modules.notice.service.NoticeService;
import wang.miansen.roothub.modules.post.model.Post;
import wang.miansen.roothub.modules.post.service.PostService;

/**
 * 
 * @author sen
 * 2018年7月3日
 * 上午10:15:55
 * TODO
 */
@Controller
public class FollowController extends AbstractBaseController<Follow, FollowDTO, FollowVO> {

	@Autowired
	private FollowService followService;
	@Autowired
	private CollectService collectDaoService;
	@Autowired
	private PostService rootTopicService;
	@Autowired
	private NoticeService rootNoticeService;

	/**
	 * 是否已关注
	 * @param fid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/follow/isFollow", method = RequestMethod.GET)
	@ResponseBody
	private Result<Integer> isFollow(String fid, HttpServletRequest request) {
		UserDTO user = null;
		if (user == null) return new Result<>(201, false, "未关注");
		if (user.getUserId() == fid) {
			return new Result<>(201, false, "同一用户");
		}
		int follow = followService.isFollow(user.getUserId(), fid);
		if (follow == 0) {
			return new Result<>(201, false, "未关注");
		}
		return new Result<>(true, follow);
	}

	/**
	 * 关注
	 * @param fid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/follow/save", method = RequestMethod.POST)
	@ResponseBody
	private Result<Integer> save(String fid, HttpServletRequest request) {
		Follow follow = new Follow();
		follow.setSourceId(getUser().getUserId().toString());
		follow.setTargetId(fid);
		follow.setCreateDate(new Date());
		int insert = followService.insert(follow);
		if (insert == 1) {
			String info = "关注成功";
			return new Result<Integer>(200, true, info);
		}
		return new Result<>(201, false, "关注失败");
	};

	/**
	 * 取消关注
	 * @param fid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/follow/delete", method = RequestMethod.POST)
	@ResponseBody
	private Result<Integer> delete(String fid, HttpServletRequest request) {
		int delete = followService.delete(getUser().getUserId().toString(), fid);
		if (delete == 1) {
			String info = "取消关注成功";
			return new Result<Integer>(200, true, info);
		}
		return new Result<>(201, false, "取消关注失败");
	}

	/**
	 * 我关注的数量
	 * @param fid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/follow/count/for", method = RequestMethod.GET)
	@ResponseBody
	private Result<Integer> countByUid(String uid, HttpServletRequest request) {
		int countByUid = followService.countByUid(uid);
		return new Result<Integer>(true, countByUid);
	}

	/**
	 * 关注我的数量
	 * @param uid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/follow/count/to", method = RequestMethod.GET)
	@ResponseBody
	private Result<Integer> countByFid(Integer fid, HttpServletRequest request) {
		int countByFid = followService.countByFid(fid);
		return new Result<Integer>(true, countByFid);
	}

	/**
	 * 特别关注
	 * @param request
	 * @param p
	 * @return
	 */
	@RequestMapping(value = "/follow/topics", method = RequestMethod.GET)
	private String followTopics(HttpServletRequest request, @RequestParam(value = "p", defaultValue = "1") Integer p) {
		UserDTO user = null;
		if (user == null) return "error-page/404.jsp";
		int countCollect = collectDaoService.count(user.getUserId());// 用户收藏话题的数量
		int countTopicByUserName = rootTopicService.countByUserName(user.getUserName());// 用户发布的主题的数量
		int notReadNotice = rootNoticeService.countNotReadNotice(user.getUserName());// 未读通知的数量
		Page<Post> pageTopic = followService.pageTopic(p, 20, user.getUserId());
		BaseEntity baseEntity = new BaseEntity();
		request.setAttribute("baseEntity", baseEntity);
		request.setAttribute("countCollect", countCollect);
		request.setAttribute("countTopicByUserName", countTopicByUserName);
		request.setAttribute("notReadNotice", notReadNotice);
		request.setAttribute("user", user);
		request.setAttribute("page", pageTopic);
		return "/default/front/follow/list";
	}

	@Override
	protected Function<? super FollowDTO, ? extends FollowVO> getDTO2VO() {
		return followDTO -> {
			FollowVO followVO = new FollowVO();
			if (followDTO != null) {
				BeanUtils.copyProperties(followDTO, followVO);
			}
			followVO.setCreateDate(DateUtils.formatDateTime(followDTO.getCreateDate()));
			followVO.setUpdateDate(DateUtils.formatDateTime(followDTO.getUpdateDate()));
			return followVO;
		};
	}

	@Override
	protected Function<? super FollowVO, ? extends FollowDTO> getVO2DTO() {
		return followVO -> {
			FollowDTO followDTO = new FollowDTO();
			if (followVO != null) {
				BeanUtils.copyProperties(followVO, followDTO);
			}
			followDTO.setCreateDate(DateUtils.string2Date(followVO.getCreateDate(), DateUtils.FORMAT_DATETIME));
			followDTO.setUpdateDate(DateUtils.string2Date(followVO.getUpdateDate(), DateUtils.FORMAT_DATETIME));
			return followDTO;
		};
	}

	@Override
	protected BaseService<Follow, FollowDTO> getService() {
		return followService;
	}

	@Override
	protected String getModuleName() {
		return "follow";
	}

	@Override
	protected QueryWrapper<Follow> getQueryWrapper() {
		return null;
	}
}