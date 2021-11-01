import * as dayjs from 'dayjs';

import('./bootstrap').catch(err => console.error(err));

const LOCALE = 'de';

import(`dayjs/locale/${LOCALE}`).then(() => {
  dayjs.locale(LOCALE);
});
