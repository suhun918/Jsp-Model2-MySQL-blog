package com.cos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.model.Board;
import com.cos.util.DBClose;

public class BoardDao {
	// 싱글톤으로 만들어야하는데 그냥 함.
	private Connection conn; // MySQL과 연결하기 위해 필요
	private PreparedStatement pstmt; // 쿼리문을 작성하고 실행하기위해 필요함
	private ResultSet rs; // 결과를 보관할 커서 받기 위해

	// 수정
	public int update(Board board) {
		final String SQL = "UPDATE board SET title = ?, content = ? WHERE id = ?";

		conn = DBConn.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getId());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		return -1;
	}

	// 삭제
	public int delete(int id) {
		final String SQL = "DELETE FROM board WHERE id = ?";
		conn = DBConn.getConnection();

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);

			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}

		return -1;
	}

	// 인기리스트 3건 가져오기
	public List<Board> findOrderByReadCountDesc() {
		final String SQL = "SELECT * FROM board ORDER BY readCount DESC limit 3";
		conn = DBConn.getConnection();

		try {
			List<Board> boards = new ArrayList<>();

			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {// rs.next() 커서이동 return 값 boolean
				Board board = new Board();
				board.setId(rs.getInt("id"));
				board.setUserId(rs.getInt("userId"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setReadCount(rs.getInt("readCount"));
				board.setCreateDate(rs.getTimestamp("createDate"));

				boards.add(board);

			}
			return boards;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}

		return null;
	}

	// 조회수 증가
	public int increaseReadCount(int id) {

		final String SQL = "UPDATE board SET readCount = readCount + 1 WHERE id = ?";

		conn = DBConn.getConnection();

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);

			int result = pstmt.executeUpdate();// 변경된 튜플(행)의 개수를 리턴

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		return -1;// 오류
	}

	public int save(Board board) {
		// 길어지면 쿼리문은 StringBuffer에 담아서 작성해도된다. 띄어쓰기는 주의!
		// 리드카운트는 디폴트가 0이라서 작성안해도된다.
		final String SQL = "INSERT INTO board(userId, title, content, createDate) VALUES(?,?,?,now())";

		conn = DBConn.getConnection();

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, board.getUserId());
			pstmt.setString(2, board.getTitle());
			pstmt.setString(3, board.getContent());
			int result = pstmt.executeUpdate();// 변경된 튜플(행)의 개수를 리턴

			return result;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt);
		}
		return -1;// 오류
	}

	/*
	 * // 리스트 보기 SELECT * FROM board ORDER BY id DESC public List<Board> findAll() {
	 * final String SQL = "SELECT * FROM board ORDER BY id DESC"; conn =
	 * DBConn.getConnection();
	 * 
	 * try { List<Board> boards = new ArrayList<>();
	 * 
	 * pstmt = conn.prepareStatement(SQL); rs = pstmt.executeQuery();
	 * 
	 * while (rs.next()) {// rs.next() 커서이동 return 값 boolean Board board = new
	 * Board(); board.setId(rs.getInt("id")); board.setUserId(rs.getInt("userId"));
	 * board.setTitle(rs.getString("title"));
	 * board.setContent(rs.getString("content"));
	 * board.setReadCount(rs.getInt("readCount"));
	 * board.setCreateDate(rs.getTimestamp("createDate"));
	 * 
	 * boards.add(board);
	 * 
	 * } return boards;
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } finally { DBClose.close(conn,
	 * pstmt, rs); }
	 * 
	 * return null; }
	 */

	// 상세 보기 SELECT * FROM board WHERE id = ?
	public Board findById(int id) {
		final String SQL = "SELECT * FROM board b, user u WHERE b.userID = u.id and b.id = ?";
		conn = DBConn.getConnection();

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				Board board = new Board();
				board.setId(rs.getInt("b.id"));
				board.setUserId(rs.getInt("b.userId"));
				board.setTitle(rs.getString("b.title"));
				board.setContent(rs.getString("b.content"));
				board.setReadCount(rs.getInt("b.readCount"));
				board.setCreateDate(rs.getTimestamp("b.createDate"));
				board.getUser().setUserProfile(rs.getString("u.userProfile"));
				board.getUser().setUsername(rs.getString("u.username"));
				return board;

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}

		return null;
	}

	// find All함수 오버로딩 해준 것
	public List<Board> findAll(int page) {

		// 한페이지에 리스트 3개씩만 보이게
		final String SQL = "SELECT * FROM board b, user u WHERE b.userId = u.id ORDER BY b.id DESC limit ?,3";
		conn = DBConn.getConnection();

		try {
			List<Board> boards = new ArrayList<>();

			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, (page - 1) * 3);
			rs = pstmt.executeQuery();

			while (rs.next()) {// rs.next() 커서이동 return 값 boolean
				Board board = new Board();
				board.setId(rs.getInt("b.id"));
				board.setUserId(rs.getInt("b.userId"));
				board.setTitle(rs.getString("b.title"));
				board.setContent(rs.getString("b.content") + " ");
				board.setReadCount(rs.getInt("b.readCount"));
				board.setCreateDate(rs.getTimestamp("b.createDate"));

				// (2) board에 user객체에 username 저장(추후 userProfile 추가)
				board.getUser().setUsername(rs.getString("u.username"));
				boards.add(board);

			}
			return boards;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}

		return null;
	}
	
	//검색용 findAll
	public List<Board> findAll(int page, String search) {

		// 한페이지에 리스트 3개씩만 보이게
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM board b, user u ");
		sb.append("WHERE b.userId = u.id and ");
		sb.append("(b.content like ? OR b.title like ?) ");
		sb.append("ORDER BY b.id DESC limit ?, 3");
		
		
		
		final String SQL = sb.toString();
		conn = DBConn.getConnection();

		try {
			List<Board> boards = new ArrayList<>();

			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setString(2, "%"+search+"%");
			pstmt.setInt(3, (page - 1) * 3);
			rs = pstmt.executeQuery();

			while (rs.next()) {// rs.next() 커서이동 return 값 boolean
				Board board = new Board();
				board.setId(rs.getInt("b.id"));
				board.setUserId(rs.getInt("b.userId"));
				board.setTitle(rs.getString("b.title"));
				board.setContent(rs.getString("b.content") + " ");
				board.setReadCount(rs.getInt("b.readCount"));
				board.setCreateDate(rs.getTimestamp("b.createDate"));

				// (2) board에 user객체에 username 저장(추후 userProfile 추가)
				board.getUser().setUsername(rs.getString("u.username"));
				boards.add(board);

			}
			return boards;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}

		return null;
	}


	public int findAll() {
		conn = DBConn.getConnection();

		final String SQL = "SELECT * FROM board";

		try {
			pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			int count = 0;

			while (rs.next()) {// rs.next(); 커서이동 return값 boolean
				count++;
			}
			// count는 게시글 수 realCount는 페이지수
			int realCount = 0;
			if (count % 3 == 0) {
				realCount = count / 3;
			} else {
				realCount = count / 3 + 1;
			}
			return realCount;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		return -1;
	}
	
	public int findAll(String search) {
		conn = DBConn.getConnection();

		final String SQL = "SELECT * FROM board WHERE title LIKE ? OR content LIKE ?";

		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setString(2, "%"+search+"%");
			rs = pstmt.executeQuery();
			int count = 0;

			while (rs.next()) {// rs.next(); 커서이동 return값 boolean
				count++;
			}
			// count는 게시글 수 realCount는 페이지수
			int realCount = 0;
			if (count % 3 == 0) {
				realCount = count / 3;
			} else {
				realCount = count / 3 + 1;
			}
			return realCount;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, pstmt, rs);
		}
		return -1;
	}
	

}
