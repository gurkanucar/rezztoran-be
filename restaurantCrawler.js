function formatRestaurants(restaurantsData) {
  const formattedRestaurants = [];

  const additionalProps = [
    "Wifi", "Engelli erişimi", "Açık hava", "Kapalı mekan", "Teras", "Bahçe",
    "Otopark", "Alkollü içecek servisi", "Kablosuz mikrofon", "Projeksiyon",
    "Ses sistemi", "Lounge", "Masaj koltuğu", "Şömine", "Canlı müzik",
    "Fenerbahçe sevgisi", "Galatasaray sevgisi", "Beşiktaş sevgisi",
    "Trabzonspor sevgisi", "Sigara içilebilir alan", "Sigara içilmeyen alan",
    "Engelli tuvaleti", "Deniz manzarası", "Bahçe manzarası", "Şehir manzarası",
    "Doğa manzarası", "Geniş kahvaltı menüsü", "Çocuk oyun alanı",
    "Deniz kenarı", "Mangal servisi", "Brunch", "Akşam yemeği", "Kahvaltı",
    "Brunch", "Bar", "Bahçe partisi", "Doğum günü partisi", "Sünnet düğünü",
    "Düğün", "Kokteyl", "Şarap menüsü", "Bira menüsü", "Kahve çeşitleri",
    "Tatlı menüsü", "Vejetaryen menü", "Kahvaltı seçenekleri",
    "Vejetaryen dostu", "Glütensiz seçenekleri", "Organik seçenekleri",
    "Bahçe manzarası", "Deniz manzarası", "Havuzbaşı manzarası", "Yemyeşil",
    "Jakuzi", "Sauna", "Mangal", "Günlük taze balık", "Açık büfe", "Fast food",
    "Sosisli sandviç", "Köfte", "Pizza", "Pide", "Lahmacun", "Dondurma",
    "Waffle", "Kahve", "Çay", "Sıcak çikolata", "Filtre kahve",
    "Akdeniz mutfağı", "Türk mutfağı", "Amerikan mutfağı", "İtalyan mutfağı",
    "Uzakdoğu mutfağı", "Japon mutfağı", "Çin mutfağı", "Kore mutfağı",
    "Lübnan mutfağı", "Hint mutfağı", "Vejetaryen burger", "Vegan seçenekleri",
    "Öğle menüsü", "Aperatifler", "Salata", "Ekmek arası yemekler", "Tatlılar",
    "Kahvaltı", "Brunch", "Akşam yemeği", "Grup etkinlikleri", "Çocuk dostu",
    "Hayvan dostu", "Oyun alanı", "Köpek parkı", "Toplantı salonu",
    "Kapalı alan", "Açık alan", "Otopark",
  ];

  for (let i = 0; i < restaurantsData.length; i++) {
    const businesses = restaurantsData[i].businesses;

    for (let j = 0; j < businesses.length; j++) {
      const restaurant = businesses[j];

      const restaurantAttributes = {};
      const numProps = Math.floor(Math.random() * 20) + 1;
      for (let k = 0; k < numProps; k++) {
        const propIndex = Math.floor(Math.random() * additionalProps.length);
        const propName = additionalProps[propIndex];
        restaurantAttributes[propName] = Math.random() < 0.5 ? "Yok" : "Var";
      }

      // Generate random busy dates
      const busyDates = [];
      const numBusyDates = Math.floor(Math.random() * 5);
      for (let k = 0; k < numBusyDates; k++) {
        const year = 2023;
        const month = Math.floor(Math.random() * 12) + 1;
        const day = Math.floor(Math.random() * 28) + 1;
        const dateString = `${year}-${month < 10 ? "0" : ""}${month}-${
            day < 10 ? "0" : ""
        }${day}`;
        busyDates.push(dateString);
      }

      const intervalMinutes = Math.floor(Math.random() * 12) * 5;

      const openingTimeHours = Math.floor(Math.random() * 8) + 7;
      const openingTimeMinutes = Math.floor(Math.random() * 2) * 30;
      const closingTimeHours = Math.floor(Math.random() * 4) + 18;
      const closingTimeMinutes = Math.floor(Math.random() * 2) * 30;

      formattedRestaurants.push({
        //id: restaurant.id,
        restaurantName: restaurant.name,
        restaurantImageList: [restaurant.image_url],
        restaurantImage: restaurant.image_url,
        city: restaurant.location.city,
        district:
            restaurant.location.address1 + " " + restaurant.location.address2,
        detailedAddress: restaurant.location.display_address.join(", "),
        latitude: restaurant.coordinates.latitude,
        longitude: restaurant.coordinates.longitude,
        starCount: restaurant.rating,
        user: {
          id: 1,
        },
        phone: restaurant.phone,
        menu: null,
        reviews: [],
        restaurantAttributes: restaurantAttributes,
        bookingAvailable: true,
        openingTime: `${openingTimeHours < 10 ? "0" : ""}${openingTimeHours}:${
            openingTimeMinutes < 10 ? "0" : ""
        }${openingTimeMinutes}`,
        closingTime: `${closingTimeHours < 10 ? "0" : ""}${closingTimeHours}:${
            closingTimeMinutes < 10 ? "0" : ""
        }${closingTimeMinutes}`,
        intervalMinutes: intervalMinutes,
        busyDates: busyDates,
      });
    }
  }

  return formattedRestaurants;
}

let data = [];

async function main() {
  const restaurants = formatRestaurants(data);
  console.log(restaurants);
}

main();
