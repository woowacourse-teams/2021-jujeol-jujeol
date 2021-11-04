import http from 'k6/http';
import { sleep, check } from 'k6';

export let options = {
  // vus: 235,
  vus: 60,
  duration: '10s',
};

export default function () {
  const before = new Date().getTime();
  const T = 2;

  let authHeaders = {
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjM1MzA2MDYyLCJleHAiOjE2NjY4NjM2NjJ9.jBL1aL7rz5LdFUdz1ekIHqhS0KuxIx-y5fNnrIv9WvY`,
    },
  }

  const preferenceRequest = JSON.stringify({
    preferenceRate: 3.0
  });

  sleep(1);
  // http.get('http://localhost:8080/drinks?page=1&sortBy=expectedPreference');
  http.put('http://localhost:8080/members/me/drinks/9/preference', preferenceRequest, authHeaders)

  // const after = new Date().getTime();
  // const diff = (after - before) / 1000;
  // const remainder = T - diff;
  // check(remainder, { 'reached request rate': remainder > 0 });
  // if (remainder > 0) {
  //   sleep(remainder);
  // } else {
  //   console.warn(`Timer exhausted! The execution time of the test took longer than ${T} seconds`);
  // }
}