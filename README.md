# 코로나 솔루션
코로나 솔루션은 코로나19 극복에 실질적인 도움이 되는 공적 마스크 재고 확인, 예방 수칙 및 현황 등을 제공합니다.  
1.1 버전입니다! UI와 성능이 개선된 2.0 버전을 제작 중입니다.  
현재 원스토어에서 만나보실 수 있습니다. 많은 다운로드 부탁드립니다. [원스토어 링크!](https://onestore.co.kr/userpoc/apps/view?pid=0000747123)

### 나도 컨트리뷰터!?
누구나, 언제나, 여러분의 도움은 환영입니다. 아래 방법을 통해 '코로나 솔루션'을 직접 개선할 수 있습니다.
1. 이슈
    - 이 코드는 문제가 있는 것 같아! 꼭 고쳐줬으면 좋겠어!
    - '코로나 솔루션'에 이 기능은 꼭 추가되었으면 해!
2. PR  
    - 이 문제는 꼭 내가 해결하고싶어!
    - 새로운 기술을 적용시켜 보고싶어!

### '코로나 솔루션'을 개발한 사람은 누구인가요?
- [홍성하](https://github.com/KRMKGOLD) (PM, Android, Design)
- [김도훈](https://github.com/kimdohun0104) (Mentor, Contributor)
- [최승민](https://github.com/choi-seung-min) (Android)

### 기능
1. **마스크 재고 확인**
2. 마스크 5부제 설명 및 계산기
3. 선별진료소, 국민안전병원 확인
4. 코로나 현황, 행동 수칙
 
### 아키텍처
![아키텍처 다이어그램](https://user-images.githubusercontent.com/36754680/81561384-0f739300-93ce-11ea-906f-a6e0d4ed1351.png)  
'코로나 솔루션'에서 다루는 자원은 마스크, 병원, 현황입니다. 공통적으로 data계층에서 처리할 로직이 적으며, 단순 처리를 하는 경우가 많습니다. 그렇기 때문에 API Client에서 모든 data로직을 처리하는 방향으로 설계했습니다. 그리고 ViewModel은 API Client에 직접 참조하는 것이 아니라, DataSource 인터페이스를 참조하고 있습니다. 그렇기 때문에 API Client는 언제든지 교체, 개선이 가능합니다. 
 
### 라이브러리
* [Android Jetpack](https://developer.android.com/jetpack/?gclid=Cj0KCQiAwP3yBRCkARIsAABGiPqdj2dwHr5d0lsRM7dkP4c9A3Ih-e2C-CHnM26xGD89-tdQpWOGes8aAlzjEALw_wcB)  
   * Foundation
      * Android KTX
      * AppCompat
   * Architecture
      * DataBinding
      * Lifecycle
      * LiveData
      * ViewModel
   * UI
      * RecyclerView
      * ConstraintLayout & MotionLayout
* [Kotlin](https://github.com/JetBrains/kotlin) 
* [ReactiveX](http://reactivex.io)
    * [RxJava2](https://github.com/ReactiveX/RxJava)
    * [RxAndroid](https://github.com/ReactiveX/RxAndroid)
* [Firebase](https://firebase.google.com)
   * [Crashlytics](https://firebase.google.com/products/crashlytics)
* Networking
   * [Retrofit](https://square.github.io/retrofit/)
   * Retrofit Gson
* [Material](https://material.io/develop/android/docs/getting-started/)
* [NaverMap](https://navermaps.github.io/android-map-sdk/guide-ko/1.html)
* [TedPermission](https://github.com/ParkSangGwon/TedPermission)

###Error 대비
Crashlytics
* https://antdevhistory.tistory.com/42
