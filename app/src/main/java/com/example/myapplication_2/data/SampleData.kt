// SampleData.kt
package com.example.myapplication_2.data

import com.example.myapplication_2.ui.model.Recipe

val sampleRecipes = listOf(
    Recipe(
        imageFileName = "morning_fresh_sandwich.jpg",
        title = "모닝 프레시 샌드위치",
        description = listOf("식빵을 토스터나 팬에 살짝 노릇하게 구워줍니다",
                "계란을 완숙으로 프라이 해줍니다",
                "토마토는 얇게 잘라주고, 상추는 씻어서 물기를 제거해줍니다",
                "빵 한 쪽에 마요네즈를 바르고 상추 -> 토마토 -> 계란 순으로 쌓아줍니다",
                "다른 빵으로 덮고 반으로 자르면 완성!"),
        rating = 1,
        author = "달콤한 피자",
        ingredients = listOf("식빵","계란","토마토","상추","마요네즈")
    ),
    Recipe(
        imageFileName = "exboyfriend_toast.jpg",
        title = "전남친 토스트",
        description = listOf("식빵을 토스터기에 구워줍니다",
                "크림치즈를 식빵에 발라줍니다",
                "블루베리잼을 발라준 뒤 전자레인지에 10초간 데워주면 완성!"),
        rating = 1,
        author = "촉촉한 토마토",
        ingredients = listOf("식빵","크림치즈","블루베리잼")
    ),
    Recipe(
        imageFileName = "soysauce_egg_rice.jpeg",
        title = "간장계란밥",
        description = listOf("밥을 그릇에 담고 간장 2큰술, 참기름 1큰술 넣고 잘 비벼줍니다",
                "그 위에 계란을 깨주고 노른자를 터뜨려줍니다",
                "전자레인지에 2분간 데워주면 완성!"),
        rating = 2,
        author = "촉촉한 토마토",
        ingredients = listOf("밥","계란","간장","참기름")
    ),
    Recipe(
        imageFileName = "tuna_mayo_rice.png",
        title = "참치마요덮밥",
        description = listOf("밥을 그릇에 담고 기름을 뺀 참치를 올려줍니다",
                "마요네즈를 뿌려서 비벼주면 완성!"),
        rating = 1,
        author = "달콤한 피자",
        ingredients = listOf("밥","참치","마요네즈")
    ),
    Recipe(
        imageFileName = "lemon_soba.png",
        title = "상큼 가득 레몬 냉소바",
        description = listOf("끓는 물에 소바면을 삶은 후 찬물에 행궈 물기를 빼줍니다",
                "기호에 맞게 시판 면츠유와 물을 섞습니다",
                "식힌 면, 레몬 한 조각, 와사비를 접시에 담고 면츠유에 면을 담가 먹으면 완성!"),
        rating = 2,
        author = "달콤한 피자",
        ingredients = listOf("소바면","레몬","면츠유","와사비")
    ),
    Recipe(
        imageFileName = "sausage_eggroll.png",
        title = "소세지 계란말이",
        description = listOf("계란 3개를 풀고 소금 약간, 우유를 넣어 잘 섞습니다",
                "소세지는 길쭉하게 썰어놓습니다",
                "달군 팬에 기름을 두르고 달걀물을 얇게 부은 후 소세지를 올리고 돌돌 말아줍니다",
                "이 과정을 2~3번 반복해서 계란말이를 두껍게 만듭니다",
                "식힌 후 먹기 좋은 크기로 썰면 완성!"),
        rating = 3,
        author = "달콤한 피자",
        ingredients = listOf("계란","소세지","소금","우유","식용유")
    ),
    Recipe(
        imageFileName = "veggie_wrap.jpg",
        title = "씩씩 채소쌈",
        description = listOf("끓는 물에 양배추 잎을 20~30초 정도 데친 후 찬물에 행구고 물기를 제거해줍니다",
                "두부는 으깨고, 양파는 잘게 썰어줍니다",
                "팬에 기름 없이 두부, 양파, 간장을 넣고 중불에서 수분이 날아가도록 볶아 속재료를 만들어줍니다",
                "데친 양배추에 속을 넣고 돌돌 말거나 동그랗게 감싼다",
                "접시에 케찹을 깔고, 그 위에 쌈볼을 얹어주면 완성!"),
        rating = 3,
        author = "촉촉한 토마토",
        ingredients = listOf("양배추잎","두부","양파","간장","케찹")
    ),
    Recipe(
        imageFileName = "carrot_fist_rice.jpg",
        title = "당근코 귀요미 주먹밥",
        description = listOf("밥에 소금간을 조금 하고 동그랗게 뭉칩니다",
                "다진 당근을 동그랗게 올려줍니다. 명란젓이나 케찹을 사용하셔도 좋습니다",
                "검은깨로 눈을, 채소잎으로 머리카락을 표현해줍니다",
                "김을 깔고 주먹밥을 얹으면 완성!"),
        rating = 1,
        author = "촉촉한 토마토",
        ingredients = listOf("밥","소금","당근","김")
    ),
    Recipe(
        imageFileName = "simple_rawbeef_bibimbap.png",
        title = "초!간!단! 육회비빔밥",
        description = listOf("밥을 그릇에 담습니다",
                "새 그릇에 고기 + 간장 + 설탕 + 참기름 + 마늘을 섞어주세요",
                "무순, 오이, 부추 등 원하는 채소를 흐르는 물에 씻고 물기 제거 후 길게 채 썰어둡니다 (생략 가능)",
                "밥 위에 양념한 육회를 올리고 채소 고명을 얹고 가운데 달걀 노른자를 올려줍니다",
                "기호에 맞게 고추장이나 깨를 곁들이면 완성!"),
        rating = 2,
        author = "달콤한 피자",
        ingredients = listOf("밥","육회","참기름","계란","간장")
    ),
    Recipe(
        imageFileName = "yubu_sushi.jpg",
        title = "곰돌이 유부초밥",
        description = listOf("밥에 초밥소스(식초 2큰술, 설탕 1큰술, 소금 0.5작은술)를 섞고 식혀줍니다",
                "유부의 물기를 제거해줍니다",
                "양념한 밥을 작게 뭉쳐 유부 속에 채워 넣고 유부를 접어 모양을 잡아줍니다",
                "취향껏 토핑을 올려 장식해주면 완성!"),
        rating = 3,
        author = "달콤한 피자",
        ingredients = listOf("밥","유부","식초","설탕","소금")
    ),
    Recipe(
        imageFileName = "beef_oilpasta.jpg",
        title = "소고기 오일파스타",
        description = listOf("물에 소금 1큰술을 넣고 면을 7~9분간 삶은 후 물기를 빼고 올리브유 1큰술로 코팅해둡니다",
                "프라이팬에 소고기를 앞뒤로 노릇하게 굽고, 소금과 후추로 간을 해줍니다",
                "고기를 따로 빼두고 같은 팬에 올리브유 2큰술 + 편마늘을 넣고 약불로 볶습니다",
                "마늘 향이 올라오면 면을 넣고 중불로 볶아줍니다. 면수를 넣어가며 간을 조절해줍니다",
                "면을 그릇에 담고 구운 소고기를 올려줍니다",
                "생잎채소를 가볍게 손질해 옆에 곁들이고 취향에 따라 후추, 고춧가루 등을 뿌려주면 완성!"),
        rating = 4,
        author = "촉촉한 토마토",
        ingredients = listOf("파스타면","소고기","소금","후추","채소")
    ),
    Recipe(
        imageFileName = "jjappaguri.jpg",
        title = "기생충 짜파구리",
        description = listOf("냄비에 물 약 600ml를 끓이고 너구리, 짜파게티의 면과 건더기스프를 넣고 끓입니다",
                "면이 익으면 물을 약 4~5큰술 정도만 남기고 모두 버립니다",
                "짜장 분말, 너구리 분말 스프를 함께 넣고 중불에서 30초 정도 볶듯이 비벼줍니다",
                "접시에 담고 계란 노른자를 올려주면 완성!"),
        rating = 2,
        author = "달콤한 피자",
        ingredients = listOf("짜파게티","너구리","계란")
    ),
    Recipe(
        imageFileName = "tteokbokki.jpg",
        title = "스트레스 해소 떡볶이",
        description = listOf("떡은 물에 10분 불려주기, 어묵과 대파를 먹기 좋은 크기로 썰어둡니다",
                "냄비에 물 300ml 넣고 끓이면서 고추장 1.5큰술, 간장 1큰술, 설탕 1큰술을 넣고 풀어줍니다",
                "고춧가루도 0.5큰술 추가해줍니다",
                "떡과 어묵을 넣고 중불~강불에서 약 10분간 졸여줍니다",
                "국물이 걸쭉해지면 대파와 삶은 계란을 올려주면 완성!"),
        rating = 3,
        author = "촉촉한 토마토",
        ingredients = listOf("떡","어묵","고추장","간장","설탕")
    ),
    Recipe(
        imageFileName = "onigiri.jpg",
        title = "판다 오니기리",
        description = listOf("밥에 소금을 약간 섞어 간을 해줍니다",
                "기름을 뺀 참치와 볶음 김치를 섞어 속을 만들어둡니다",
                "속을 넣고 삼각형 형태로 밥을 뭉쳐줍니다",
                "김을 가위로 잘라서 판다의 눈, 귀, 코를 만들고 조심스럽게 붙여주면 완성!"),
        rating = 2,
        author = "촉촉한 토마토",
        ingredients = listOf("밥","김","참치","김치","소금")
    ),
    Recipe(
        imageFileName = "tuna_kimchi_stew.jpg",
        title = "엄마가 끓여준 참치김치찌개",
        description = listOf("김치를 국그릇에 가득 담길 정도의 양을 준비해줍니다. 덜 익은 김치라면 식초 한 스푼을 뿌려줍니다",
                "냄비에 식용유 3큰술을 뿌리고 김치와 다진 마늘을 약~중불에서 볶아줍니다",
                "참치 1캔을 기름과 함께 넣어줍니다",
                "물 400ml, 김치국물 3큰술을 넣고 팔팔 끓여줍니다",
                "썰어둔 대파와 고춧가루를 기호에 맞게 넣고 조금 더 끓어주면 완성!"),
        rating = 3,
        author = "달콤한 피자",
        ingredients = listOf("김치","참치","마늘","식용유","대파")
    ),
    Recipe(
        imageFileName = "rajook.jpg",
        title = "입맛이 없을 땐 라죽~",
        description = listOf("라면에서 분말스프와 건더기스프를 빼고 면을 잘게 부숴줍니다",
                "냄비에 물 500ml를 붓고 분말스프와 건더기스프를 모두 넣고 강불로 끓여줍니다",
                "부숴둔 면과 밥 반공기 분량을 같이 넣고 중불에서 푹 끓여줍니다",
                "면발과 밥이 부드럽게 퍼지듯 익으면 참기름을 조금 둘러주고 그릇에 옮겨 담으면 완성!"),
        rating = 1,
        author = "촉촉한 토마토",
        ingredients = listOf("라면","참기름","계란","밥")
    ),
    Recipe(
        imageFileName = "easy_shabushabu.jpg",
        title = "초!간!단! 샤브샤브",
        description = listOf("냄비에 물과 간장을 넣고 팔팔 끓여줍니다",
                "양배추 -> 숙주 -> 청경채 -> 버섯 순으로 넣고 3분 정도 끓여줍니다",
                "추가적인 간은 국간장, 소금으로 조절해줍니다",
                "먹기 직전에 차돌박이를 넣어서 살짝 익혀주면 완성!"),
        rating = 3,
        author = "달콤한 피자",
        ingredients = listOf("차돌박이","간장","소금","양배추","숙주")
    ),
    Recipe(
        imageFileName = "crab_friedrice.jpg",
        title = "게맛살볶음밥",
        description = listOf("게맛살을 결대로 찢고, 양파를 썰어둡니다",
                "팬에 기름을 두르고 계란을 스크램블처럼 익힌 뒤 꺼내둡니다",
                "같은 팬에 식용유를 두르고 양파를 볶아줍니다",
                "밥을 넣고 잘 풀어준 후 게맛살과 볶아둔 계란 넣고 간장으로 간을 하며 볶아줍니다",
                "마지막에 소금과 후추로 간을 해주고 기호에 따라 참기름이나 통깨를 뿌려주면 완성!"),
        rating = 4,
        author = "달콤한 피자",
        ingredients = listOf("맛살","밥","계란","소금","간장")
    ),
    Recipe(
        imageFileName = "eggjjim.jpg",
        title = "뭉게뭉게 계란찜",
        description = listOf("계란 3개를 뚝배기에 깨뜨려 넣은 뒤 거픔기로 계란을 풀어줍니다",
                "설탕 1/3큰술, 소금 1/3큰술을 넣고 거품기로 잘 섞어줍니다",
                "물 100ml를 넣어주고 거품기로 또 섞어줍니다",
                "뚝배기를 중불에 올린 뒤 계란이 익을 때까지 젓가락으로 저어줍니다",
                "어느 정도 몽글몽글해지면 썰어둔 쪽파를 넣고 뚜껑을 닫고 4~5분간 익혀주면 완성!"),
        rating = 2,
        author = "촉촉한 토마토",
        ingredients = listOf("계란","설탕","소금","쪽파")
    ),
    Recipe(
        imageFileName = "yeolmu_noodle.jpg",
        title = "시원한 열무국수",
        description = listOf("열무김치와 김치국물을 반 컵 준비합니다",
                "고명으로 오이를 약간 채 썰고 달걀도 삶아둡니다",
                "냄비에 물 1L를 넣고 끓어오를 때 소면을 넣어줍니다",
                "끓어오르면 차가운 물을 부어주고 면이 적당히 삶아졌다면 흐르는 차가운 물에 헹궈줍니다",
                "그릇에 소면을 올린 후 열무김치를 올려줍니다",
                "약간 해동한 냉면 육수와 김치국물을 섞어 그릇에 부어주고 고명을 올리면 완성!"),
        rating = 2,
        author = "달콤한 피자",
        ingredients = listOf("소면","냉면육수","열무김치","오이","계란")
    )
)
