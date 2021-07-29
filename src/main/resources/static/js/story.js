function getStoryItem(image) {
    let item = `<div class="story-list__item">
                <div class="sl__item__header">
                \t<div>
                \t\t<img class="profile-image" src="/upload/${image.user.profileImageUrl}"
                \t\t\tonerror="this.src='/images/person.jpeg'" />
                \t</div>
                \t<div>${image.user.username}</div>
                </div>
                
                <div class="sl__item__img">
                \t<img src="/upload/${image.postImageUrl}" />
                </div>
                
                <div class="sl__item__contents">
                \t<div class="sl__item__contents__icon">
                
                \t\t<button>`;
                    if(image.likeState){
                        item += `\t\t\t<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
                    } else {
                        item += `\t\t\t<i class="fa-heart far" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`;
                    }
                    item += `
                \t\t</button>
                \t</div>
                
                \t<span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount}</b>likes</span>
                
                \t<div class="sl__item__contents__content">
                \t\t<p>${image.caption}</p>
                \t</div>
                
                \t<div id="storyCommentList-${image.id}">`;

                image.comments.forEach(comment=>{
                    item += `
                    <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
                        <p>
                            <b>${comment.user.username} :</b> ${comment.content}
                        </p>
                        <button>
                            <i class="fas fa-times"></i>
                        </button>
                    </div>
                    `;
                });

                item +=`
                \t</div>
                
                \t<div class="sl__item__input">
                \t\t<input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
                \t\t<button type="button" onClick="addComment(${image.id})">게시</button>
                \t</div>
                
                </div>`;
    return item;
}
/**
 2. 스토리 페이지
 (1) 스토리 로드하기
 (2) 스토리 스크롤 페이징하기
 (3) 좋아요, 안좋아요
 (4) 댓글쓰기
 (5) 댓글삭제
 */

// (1) 스토리 로드하기
let page = 0;

function storyLoad() {
    $.ajax({
        url: `/api/image?page=${page}`,
        dataType: "json"
    }).done(res => {
        console.log(res);
        res.data.content.forEach(image=> {
        	let storyItem = getStoryItem(image);
        	$("#storyList").append(storyItem);
		});
    }).fail(error => {
        console.log(error);
    });
}

storyLoad();

// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
    // console.log("윈도우스크롤탑",$(window).scrollTop());
    // console.log("문서높이",$(document).height());
    // console.log("윈도우높이",$(window).height());
    // 윈도우스크롤탑은 = 문서높이 - 윈도우높이 즉 윈도우 스크롤탑이 문서높이-윈도우높이를 뺸값이랑 같을 떄 스크롤 페잊징이 발생하면됨.
    let checkNum = $(window).scrollTop() - ($(document).height() - $(window).height());
    // console.log(checkNum);

    if(checkNum < 1 && checkNum > -1) {
        page++;
        storyLoad();
    }
});


// (3) 좋아요, 좋아요취소
function toggleLike(imageId) {
    let likeIcon = $(`#storyLikeIcon-${imageId}`);
    if (likeIcon.hasClass("far")) { //좋아요
        $.ajax({
            type:"post",
            url: `/api/image/${imageId}/likes`,
            dataType: "json"
        }).done(res=>{
            let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
            let likeCount = Number(likeCountStr) + 1;
            $(`#storyLikeCount-${imageId}`).text(likeCount);
            likeIcon.addClass("fas");
            likeIcon.addClass("active");
            likeIcon.removeClass("far");
        }).fail(error=>{
            console.log("오류",error);
        });
    } else { //좋아요 취소
        $.ajax({
            type:"delete",
            url: `/api/image/${imageId}/likes`,
            dataType: "json"
        }).done(res=>{
            let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
            let likeCount = Number(likeCountStr) - 1;
            $(`#storyLikeCount-${imageId}`).text(likeCount);
            likeIcon.removeClass("fas");
            likeIcon.removeClass("active");
            likeIcon.addClass("far");
        }).fail(error=>{
            console.log("오류",error);
        });

    }
}

// (4) 댓글쓰기
function addComment(imageId) {

    let commentInput = $(`#storyCommentInput-${imageId}`);
    let commentList = $(`#storyCommentList-${imageId}`);

    let data = {
        imageId: imageId,
        content: commentInput.val()
    }

    if (data.content === "") {
        alert("댓글을 작성해주세요!");
        return;
    }
    $.ajax({
        type:"post",
        url:"/api/comment",
        data:JSON.stringify(data),
        contentType:"application/json; charset=utf-8",
        dataType:"json"
    }).done(res=>{
        console.log("성공", res);
        let comment = res.data;
        let content = `
			  <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
			    <p>
			      <b>${comment.user.username} :</b>
			      ${comment.content}
			    </p>
			    <button><i class="fas fa-times"></i></button>
			  </div>
	    `;
        commentList.prepend(content); //최신댓글이 위로 prepend
    }).fail(error=>{
        console.log("오류", error);
    });
    commentInput.val("");
}

// (5) 댓글 삭제
function deleteComment() {

}







