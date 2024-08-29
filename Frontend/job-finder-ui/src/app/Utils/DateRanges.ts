export interface TimeRange {
  label: string;
  value: string;
}

export const TIME_RANGES: TimeRange[] = [
  { label: 'LAST WEEK', value: 'LAST_WEEK' },
  { label: 'LAST MONTH', value: 'LAST_MONTH' },
  { label: 'LAST SIX MONTHS', value: 'LAST_SIX_MONTH' },
  { label: 'ANYTIME', value: 'ANYTIME' }
];
