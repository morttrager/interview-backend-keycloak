import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IInterviewQuestionSet, InterviewQuestionSet } from 'app/shared/model/interview-question-set.model';
import { InterviewQuestionSetService } from './interview-question-set.service';
import { InterviewQuestionSetComponent } from './interview-question-set.component';
import { InterviewQuestionSetDetailComponent } from './interview-question-set-detail.component';
import { InterviewQuestionSetUpdateComponent } from './interview-question-set-update.component';

@Injectable({ providedIn: 'root' })
export class InterviewQuestionSetResolve implements Resolve<IInterviewQuestionSet> {
  constructor(private service: InterviewQuestionSetService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInterviewQuestionSet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((interviewQuestionSet: HttpResponse<InterviewQuestionSet>) => {
          if (interviewQuestionSet.body) {
            return of(interviewQuestionSet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InterviewQuestionSet());
  }
}

export const interviewQuestionSetRoute: Routes = [
  {
    path: '',
    component: InterviewQuestionSetComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InterviewQuestionSets',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InterviewQuestionSetDetailComponent,
    resolve: {
      interviewQuestionSet: InterviewQuestionSetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InterviewQuestionSets',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InterviewQuestionSetUpdateComponent,
    resolve: {
      interviewQuestionSet: InterviewQuestionSetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InterviewQuestionSets',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InterviewQuestionSetUpdateComponent,
    resolve: {
      interviewQuestionSet: InterviewQuestionSetResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'InterviewQuestionSets',
    },
    canActivate: [UserRouteAccessService],
  },
];
