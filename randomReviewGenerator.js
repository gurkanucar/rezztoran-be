
const userIds = [1, 2, 3, 4, 5, 6];
const restaurantIds = Array.from({ length: 89 }, (_, i) => i + 1);

const reviews = [];
for (let i = 0; i < 90; i++) {
  const userId = userIds[Math.floor(Math.random() * userIds.length)];
  const restaurantId = restaurantIds[Math.floor(Math.random() * restaurantIds.length)];

  const existingReview = reviews.find(review => review.userId === userId && review.restaurantId === restaurantId);
  if (existingReview) {
    i--;
    continue;
  }

  let starCount;
  let commentContents;

  const randomSelection = Math.random();
  if (randomSelection < 0.3) {
    starCount = Math.floor(Math.random() * 3) + 3;
    commentContents = [
      "Çok lezzetliydi!",
      "Harika bir deneyim yaşadık.",
      "Hızlı servis ve kaliteli yemekler.",
      "Kesinlikle tavsiye ederim.",
      "Personel çok ilgiliydi.",
      "Fiyat performans dengesi mükemmel.",
      "Muhteşem manzarasıyla unutulmaz bir mekan.",
      "Yemekler taptaze ve lezzetliydi."
    ];
  } else if (randomSelection < 0.7) {

    starCount = Math.floor(Math.random() * 2) + 2;
    commentContents = [
      "tavsiye ederim.",
      "guzel.",
      "Fiyat performans",
      "idare eder",
      "eh iste"
    ];
  } else {

    starCount = Math.floor(Math.random() * 2) + 1;
    commentContents = [
      "Yemekler beklenenden kötüydü.",
      "Hizmet kalitesi düşük.",
      "Bir daha gitmeyi düşünmüyorum.",
      "igrenc",
      "hic sevmedim",
      "bidaha gelmem"
    ];
  }

  const content = commentContents[Math.floor(Math.random() * commentContents.length)];

  const review = {
    userId: userId,
    restaurantId: restaurantId,
    star: starCount,
    content: content
  };

  reviews.push(review);
}

console.log(reviews);
