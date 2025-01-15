package com.example.booktracker.utils;

import android.text.TextUtils;

import com.example.booktracker.R;
import com.example.booktracker.db.BookDB;
import com.example.booktracker.db.BusinessResult;
import com.example.booktracker.entity.Book;

import java.util.List;

public class DataUtils {

    /**
     * 当前数据编辑以后，需要卸载app，重新安装才能生效
     */
    public static void init() {
        // 初始化数据
        BusinessResult<List<Book>> allBookListResult = BookDB.getAllBooks();
        if (allBookListResult.isSuccess() && !allBookListResult.getData().isEmpty()) {
            return;
        }
        //存储图片
        String book1url = ImagePreStorageUtils.saveDrawableImage(R.drawable.book1, "book1");
        String book2url = ImagePreStorageUtils.saveDrawableImage(R.drawable.book2, "book2");
        String book3url = ImagePreStorageUtils.saveDrawableImage(R.drawable.book3, "book3");
        String book4url = ImagePreStorageUtils.saveDrawableImage(R.drawable.book4, "book4");
        String book5url = ImagePreStorageUtils.saveDrawableImage(R.drawable.book5, "book5");
        String book6url = ImagePreStorageUtils.saveDrawableImage(R.drawable.book6, "book6");
        String book7url = ImagePreStorageUtils.saveDrawableImage(R.drawable.book7, "book7");
        String book8url = ImagePreStorageUtils.saveDrawableImage(R.drawable.book8, "book8");
        String book9url = ImagePreStorageUtils.saveDrawableImage(R.drawable.book9, "book9");
        //添加一些预设的图书，方便测试
        if (!TextUtils.isEmpty(book1url)) {
            Book book1 = new Book();
            book1.setName("思辨力35讲：像辩手一样思考");
            book1.setAuthor("庞颖");
            book1.setDesc("本书是一套帮助你识破逻辑陷阱，迅速提升分析、表达问题的实用指南。在工作中遇到分歧，如何有效沟通、准确表达？面对热搜和复杂的公共社会议题，如何获得理性视角，达成共识？在与家人、朋友讨论时，如何识别逻辑谬误，抓住问题的核心，不被牵着鼻子走？");
            book1.setUrl(book1url);
            book1.setTotal(10);
            BookDB.addBook(book1);
        }
        if (!TextUtils.isEmpty(book2url)) {
            Book book2 = new Book();
            book2.setName("作家榜名著：浮士德");
            book2.setAuthor("歌德");
            book2.setDesc("永恒的女性，指引我们向上。满腹才学的浮士德，厌倦了长期的书斋生活，对未来感到迷茫和不安，不知该何去何从。在极端苦恼的状态下，他卷入了魔鬼和上帝之间的赌约，为了追求新的生活，他与魔鬼签下协议：以灵魂作抵押，换魔鬼满足他对尘世生活的一切要求。");
            book2.setUrl(book2url);
            book2.setTotal(10);
            BookDB.addBook(book2);
        }
        if (!TextUtils.isEmpty(book3url)) {
            Book book3 = new Book();
            book3.setName("生气的时候就看看：365日育儿减压指南");
            book3.setAuthor("亲野智可");
            book3.setDesc("没有人生来就知道如何成为好的父母，事实上，每个人都是在不安中学习和成长，逐渐适应为人父母的角色，学会正确地与孩子相处的。");
            book3.setUrl(book3url);
            book3.setTotal(10);
            BookDB.addBook(book3);
        }
        if (!TextUtils.isEmpty(book4url)) {
            Book book4 = new Book();
            book4.setName("我不是废柴（都市剧《凡人歌》原著）");
            book4.setAuthor("纪静蓉");
            book4.setDesc("人到中年，身不由己。有人996搏命，有人遁世躺平，“废”得花样百出，“卷”得千奇百怪，面对各自的人生窘境，他们正视惨淡现实，共渡人生危机。剧版《凡人歌》原著小说，殷桃，王骁领衔主演。");
            book4.setUrl(book4url);
            book4.setTotal(10);
            BookDB.addBook(book4);
        }
        if (!TextUtils.isEmpty(book5url)) {
            Book book5 = new Book();
            book5.setName("人生“歪”理，“歪”得很有道理");
            book5.setAuthor("多嘴鸭著绘");
            book5.setDesc("你是否跟我一样，有一颗追求安稳的心，又时刻做着不切实际的梦；明明被生活捶打得遍体鳞伤，还在倔强地说无怨无悔；心理藏着苦，眼里含着泪，嘴角还要保持微笑……");
            book5.setUrl(book5url);
            book5.setTotal(10);
            BookDB.addBook(book5);
        }
        if (!TextUtils.isEmpty(book6url)) {
            Book book6 = new Book();
            book6.setName("茉莉与蔷薇：女医药销售那些事儿");
            book6.setAuthor("羽琼");
            book6.setDesc("或许只有女人才能理解另一个女人的难，只有女人才能真正帮助另一个女人。娇妻文学爱好者、直男癌晚期患者慎入！献给每一个从泥潭中盛放的她！");
            book6.setUrl(book6url);
            book6.setTotal(10);
            BookDB.addBook(book6);
        }
        if (!TextUtils.isEmpty(book7url)) {
            Book book7 = new Book();
            book7.setName("火葬场里的生死课：女入殓师工作手记");
            book7.setAuthor("孙留仙");
            book7.setDesc("本书是16岁入行的女入殓师孙留仙的9年从业手记，记录了一个处心积虑想到殡仪馆上班的“鬼马”少女，从刚入行时对这份职业的懵懂无知到认真学习遗体化妆，从而在这个岗位上独当一面的故事。");
            book7.setUrl(book7url);
            book7.setTotal(10);
            BookDB.addBook(book7);
        }
        if (!TextUtils.isEmpty(book8url)) {
            Book book8 = new Book();
            book8.setName("悲剧的诞生");
            book8.setAuthor("尼采");
            book8.setDesc("作为一部美学经典，尼采在书中解开悲剧美学之谜，即希腊悲剧是由于日神阿波罗精神与酒神狄奥尼索斯精神的对抗与调和而产生的，并以酒神精神为主导；主张在悲剧的痛苦中感受到一种更高的、征服的欢乐，看到生命乃永恒的美。");
            book8.setUrl(book8url);
            book8.setTotal(10);
            BookDB.addBook(book8);
        }
        if (!TextUtils.isEmpty(book9url)) {
            Book book9 = new Book();
            book9.setName("这就是人性（合集）");
            book9.setAuthor("王心傲");
            book9.setDesc("有人的地方就有江湖，有人际交流的地方就有博弈与制衡。任何对于沟通技巧和销售技巧的学习，都是经营人际关系的基础。");
            book9.setUrl(book9url);
            book9.setTotal(10);
            BookDB.addBook(book9);
        }
    }
}
