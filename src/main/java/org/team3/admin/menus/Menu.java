package org.team3.admin.menus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Menu {
    private final static Map<String, List<MenuDetail>> menus;

    static {
        menus = new HashMap<>();
        menus.put("member", Arrays.asList(
                new MenuDetail("list", "회원목록", "/admin/member"),
                new MenuDetail("add", "회원등록", "/admin/member/add")
        ));

        menus.put("board", Arrays.asList(
                new MenuDetail("list", "게시판목록", "/admin/board"),
                new MenuDetail("add", "게시판등록", "/admin/board/add"),
                new MenuDetail("posts", "게시글관리", "/admin/board/posts")
        ));

        menus.put("profile", Arrays.asList(
                new MenuDetail("profile", "내프로필", "/front/mypage/profile"),
                new MenuDetail("myBoard", "내활동", "/front/mypage/myBoard"),
                new MenuDetail("follow", "팔로우팔로잉", "/front/mypage/follow"),
                new MenuDetail("delete", "회원탈퇴", "/front/mypage/deleteMember")
        ));

        menus.put("option", Arrays.asList(
                new MenuDetail("list", "보조지표목록", "/admin/option"),
                new MenuDetail("add", "보조지표등록", "/admin/option/add"),
                new MenuDetail("rank", "보조지표 랭킹등록", "/admin/option/rank")
        ));
    }

    public static List<MenuDetail> getMenus(String code) {
        return menus.get(code);
    }
}