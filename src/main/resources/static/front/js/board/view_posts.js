window.addEventListener("DOMContentLoaded", function(){
    const { ajaxLoad } = commonLib;

    let viewPosts = localStorage.getItem('viewPosts');
    if(!viewPosts) {
        return;
    }

    viewPosts = JSON.parse(viewPosts);

    const qs = viewPosts.map(p => `seq=${p.seq}`).join('&');
    const tpl = document.getElementById("posts_tpl").innerHTML;
    const targetEl = document.querySelector(".view_posts");

    const domParser = new DOMParser();
    ajaxLoad('GET', `/api/board/view_post?${qs}`, null, 'json')
        .then(res => {
            if (!res.success || !res.data) return;
            const items = res.data;

            for (const item of items) {
                let html = tpl;
                const createdAtDate = new Date(item.createdAt);
                const formattedDate = `${createdAtDate.getFullYear()}.${(createdAtDate.getMonth() + 1).toString().padStart(2, '0')}.${createdAtDate.getDate().toString().padStart(2, '0')} ${createdAtDate.getHours().toString().padStart(2, '0')}:${createdAtDate.getMinutes().toString().padStart(2, '0')}`;


                html = html.replace(/\[url\]/g, `/board/view/${item.seq}`)
                            .replace(/\[subject\]/g, item.subject)
                            .replace(/\[bid\]/g, item.board.bid)
                            .replace(/\[userId\]/g,item.poster)
                            .replace(/\[createdAt\]/g, formattedDate)
                            .replace(/\[visitcount\]/g, item.viewCount);

                const dom = domParser.parseFromString(html, "text/html");
                const li = dom.querySelector("li");
                targetEl.appendChild(li);
            }
        })
        .catch(err => console.error(err));

});